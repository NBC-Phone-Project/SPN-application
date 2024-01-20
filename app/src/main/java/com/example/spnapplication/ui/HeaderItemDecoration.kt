package com.example.spnapplication.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HeaderItemDecoration(
    recyclerView: RecyclerView,
    private val isHeader: (itemPosition: Int) -> Boolean,
) : RecyclerView.ItemDecoration() {

    private var currentHeaderToShow: Pair<Int, RecyclerView.ViewHolder>? = null

    init {
        recyclerView.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                // clear saved header as it can be outdated now
                currentHeaderToShow = null
            }
        })

        recyclerView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            // clear saved layout as it may need layout update
            currentHeaderToShow = null
        }
    }

    override fun onDrawOver(c: Canvas, recyclerView: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, recyclerView, state)

        val topChildView = getTopChildView(recyclerView) ?: return
        val topChildViewPosition = recyclerView.getChildAdapterPosition(topChildView)
        if (topChildViewPosition == RecyclerView.NO_POSITION) return

        val headerView = getHeaderViewToShow(topChildViewPosition, recyclerView) ?: return

        val contactedNewHeader = getContactedNewHeader(headerView, recyclerView)
        if (contactedNewHeader != null) {
            drawMovedHeader(c, headerView, contactedNewHeader, recyclerView.paddingTop)
        } else {
            drawHeader(c, headerView, recyclerView.paddingTop)
        }
    }

    private fun getTopChildView(recyclerView: RecyclerView)
            = recyclerView.findChildViewUnder(
        recyclerView.paddingStart.toFloat(),
        recyclerView.paddingTop.toFloat()
    )

    private fun getHeaderViewToShow(topChildItemPosition: Int, recyclerView: RecyclerView): View? {
        recyclerView.adapter ?: return null

        val headerPositionToShow = getHeaderPositionToShow(topChildItemPosition)
        if (headerPositionToShow == RecyclerView.NO_POSITION) return null

        return getHeaderView(headerPositionToShow, recyclerView)
    }

    private fun getHeaderView(headerPositionToShow: Int, recyclerView: RecyclerView): View? {
        val headerHolderType = recyclerView.adapter!!.getItemViewType(headerPositionToShow)
        if (currentHeaderToShow?.first == headerPositionToShow && currentHeaderToShow?.second?.itemViewType == headerHolderType) {
            return currentHeaderToShow?.second?.itemView
        }

        val headerHolder = recyclerView.adapter!!.createViewHolder(recyclerView, headerHolderType)
        recyclerView.adapter!!.onBindViewHolder(headerHolder, headerPositionToShow)
        fixLayoutSize(recyclerView, headerHolder.itemView)

        currentHeaderToShow = headerPositionToShow to headerHolder

        return headerHolder.itemView
    }

    private fun getHeaderPositionToShow(topChildItemPosition: Int): Int {
        var headerPosition = RecyclerView.NO_POSITION
        var currentPosition = topChildItemPosition
        do {
            if (isHeader(currentPosition)) {
                headerPosition = currentPosition
                break
            }
            currentPosition -= 1
        } while (currentPosition >= 0)
        return headerPosition
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {
        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingStart + parent.paddingEnd,
            view.layoutParams.width
        )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )

        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun drawHeader(c: Canvas, header: View, paddingTop: Int) {
        c.save()
        c.translate(0f, paddingTop.toFloat())
        header.draw(c)
        c.restore()
    }

    private fun getContactedNewHeader(headerView: View, recyclerView: RecyclerView): View? {
        val contactPoint = headerView.bottom + recyclerView.paddingTop
        val contactedChildView = getContactedChildView(recyclerView, contactPoint) ?: return null
        val contactedChildViewPosition = recyclerView.getChildAdapterPosition(contactedChildView)

        return if (isHeader(contactedChildViewPosition)) {
            contactedChildView
        } else {
            null
        }
    }

    private fun getContactedChildView(recyclerView: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until recyclerView.childCount) {
            val child = recyclerView.getChildAt(i)
            val bounds = Rect()
            recyclerView.getDecoratedBoundsWithMargins(child, bounds)
            if (bounds.bottom > contactPoint) {
                if (bounds.top <= contactPoint) {
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    private fun drawMovedHeader(c: Canvas, contactedTopHeader: View, contactedBottomHeader: View, paddingTop: Int) {
        c.save()
        c.translate(0f, (contactedBottomHeader.top - contactedTopHeader.height).toFloat())
        contactedTopHeader.draw(c)
        c.restore()
    }

    private fun initHeaderClickListener(recyclerView: RecyclerView, onClickHeader: (Int) -> Unit) {
        recyclerView.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(
                recyclerView: RecyclerView,
                motionEvent: MotionEvent
            ): Boolean {
                return if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    val hasClickedOnHeaderArea = motionEvent.y <= currentHeaderToShow?.second?.itemView?.bottom ?: 0
                    if (hasClickedOnHeaderArea) {
                        currentHeaderToShow?.first?.let { position ->
                            onClickHeader.invoke(position)
                        }
                        true
                    } else {
                        false
                    }
                } else false
            }
        })
    }
}
