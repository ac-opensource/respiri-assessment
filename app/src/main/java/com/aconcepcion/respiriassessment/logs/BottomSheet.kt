package com.aconcepcion.respiriassessment.logs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.NestedScrollView
import com.aconcepcion.respiriassessment.R
import com.aconcepcion.respiriassessment.databinding.LayoutBottomSheetBinding
import com.aconcepcion.respiriassessment.utils.toPx
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Modal bottom sheet. This shows a bottom sheet using [BottomSheetDialogFragment].
 */
abstract class BottomSheet: BottomSheetDialogFragment(),
        NestedScrollView.OnScrollChangeListener
{

    companion object {
        /**
         * Default value of [isDraggable] variable
         */
        private const val DEFAULT_DRAGGABLE = true
        /**
         * Default value of [isCollapsible] variable
         */
        private const val DEFAULT_COLLAPSIBLE = true
    }

    /**
     * Indicate that user can drag the BottomSheet or not
     */
    var isDraggable: Boolean = DEFAULT_DRAGGABLE
        set(value) {
            field = value
            behavior?.isDraggable = value
        }
    /**
     * Indicate that user can drag the BottomSheet or not
     * This variable should be set before [onCreateDialog] event is called. For example, set it
     * int [onCreate] event
     */
    var isCollapsible: Boolean = DEFAULT_COLLAPSIBLE
    var behavior: BottomSheetBehavior<*>? = null
    private lateinit var binding: LayoutBottomSheetBinding

    /**
     * Get the content view of the BottomSheet
     */
    abstract fun getContentView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutBottomSheetBinding.inflate(inflater, container, false)
        binding.vContent.addView(getContentView(inflater, container, savedInstanceState))
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            bottomSheetDialog.findViewById<FrameLayout>(
                    com.google.android.material.R.id.design_bottom_sheet
            )?.let { bottomSheet ->
                behavior = BottomSheetBehavior.from(bottomSheet).apply {
                    skipCollapsed = !isCollapsible
                    if (skipCollapsed) {
                        state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
            }

        }
        bottomSheetDialog.dismissWithAnimation = true
        return bottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewEvents()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViewEvents() {
        binding.vScroll.setOnScrollChangeListener(this)

        binding.vScroll.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    behavior?.isDraggable = isDraggable && binding.vScroll.scrollY == 0
                }
                MotionEvent.ACTION_UP -> {
                    behavior?.isDraggable = isDraggable
                }
            }
            false
        }
    }

    override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldX: Int, oldY: Int) {
        updateHeaderShadow(scrollY == 0)
    }

    /**
     * Update shadow (elevation) of the header when the content is being scrolled
     * @param isScrolledToTop indicate that the scroll view is scrolled to the top most or not
     */
    private fun updateHeaderShadow(isScrolledToTop: Boolean) {
        if (binding.vNavigation.childCount == 0) return

        if (isScrolledToTop) {
            binding.vHeader.apply {
                elevation = 0f
                translationZ = 0f
            }
        } else {
            binding.vHeader.apply {
                elevation = 1.toPx
                translationZ = 6.toPx
            }
        }
    }

    /**
     * Add a header view on top of the bottom sheet content.
     * This header will be scrolled along with the content
     *
     * @param view the header view
     */
    fun addContentHeader(view: View) {
        binding.vContent.addView(view, 0)
    }

    /**
     * Add a navigation view to the sticky header.
     * This header will NOT be scrolled along with the content
     *
     * @param view the navigation view
     */
    fun addStickyNavigation(view: View) {
        binding.vNavigation.addView(view)
    }
}