package es.avifer.pageindicator

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.avifer.pageindicator.databinding.ViewIndicatorBinding

@Suppress("unused", "WeakerAccess", "MemberVisibilityCanBePrivate")
class PageIndicator(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    companion object {
        private val DEFAULT_BACKGROUND_INDICATOR = R.drawable.view__indicator_default_drawable
        private val DEFAULT_COLOR_SELECTED_INDICATOR = R.color.purple_500
        private val DEFAULT_COLOR_UNSELECTED_INDICATOR = R.color.purple_200

        private val DEFAULT_SIZE_INDICATOR = R.dimen.view__indicator__label__default_size
        private val DEFAULT_MARGIN_HORIZONTAL_INDICATOR =
            R.dimen.view__indicator__label__default_margin_horizontal

        private const val DEFAULT_QUANTITY_INDICATORS = 1
    }

    //region VIEWS

    private var listIndicators: RecyclerView? = null

    //endregion

    //region VARS CONFIG

    private var adapterListIndicator: PageIndicatorAdapter? = null

    //endregion

    //region ATTRIBUTES

    private var attributes: TypedArray? = null

    //endregion


    init {
        inflate(context, R.layout.view__page_indicator, this)
        this.attributes = context.obtainStyledAttributes(attributeSet, R.styleable.PageIndicator)
        initListIndicators()
    }

    //region INIT LIST SELECTORS

    private fun initListIndicators() {
        listIndicators = findViewById(R.id.view__page_indicator__list__elements)
        initAdapterListIndicator()
        initDrawableBackgroundIndicator()
        initColorSelectedIndicator()
        initColorUnselectedIndicator()
        initSizeIndicator()
        initMarginHorizontalIndicator()
        initQuantityIndicator()
    }

    private fun initAdapterListIndicator() {
        adapterListIndicator =
            PageIndicatorAdapter(
                DEFAULT_BACKGROUND_INDICATOR,
                DEFAULT_COLOR_SELECTED_INDICATOR,
                DEFAULT_COLOR_UNSELECTED_INDICATOR,
                resources.getDimension(DEFAULT_SIZE_INDICATOR).toInt(),
                resources.getDimension(DEFAULT_MARGIN_HORIZONTAL_INDICATOR).toInt(),
            ).apply { submitList(SelectorModel.createList(DEFAULT_QUANTITY_INDICATORS)) }

        listIndicators?.adapter = adapterListIndicator
    }

    private fun initDrawableBackgroundIndicator() {
        attributes?.getResourceId(
            R.styleable.PageIndicator_drawableBackgroundIndicator,
            DEFAULT_BACKGROUND_INDICATOR
        )?.let {
            setIdResDrawableBackgroundIndicator(it)
        }
    }

    private fun initColorSelectedIndicator() {
        attributes?.getResourceId(
            R.styleable.PageIndicator_colorSelectedPageIndicator,
            DEFAULT_COLOR_SELECTED_INDICATOR
        )?.let {
            setIdColorSelectedIndicator(it)
        }
    }

    private fun initColorUnselectedIndicator() {
        attributes?.getResourceId(
            R.styleable.PageIndicator_colorUnselectedPageIndicator,
            DEFAULT_COLOR_UNSELECTED_INDICATOR
        )?.let {
            setIdColorUnselectedIndicator(it)
        }
    }

    private fun initSizeIndicator() {
        attributes?.getLayoutDimension(
            R.styleable.PageIndicator_sizeIndicator,
            resources.getDimension(DEFAULT_SIZE_INDICATOR).toInt()
        )?.let {
            setSizeIndicator(it)
        }
    }

    private fun initMarginHorizontalIndicator() {
        attributes?.getLayoutDimension(
            R.styleable.PageIndicator_marginHorizontalIndicator,
            resources.getDimension(DEFAULT_MARGIN_HORIZONTAL_INDICATOR).toInt()
        )?.let {
            setMarginHorizontalIndicator(it)
        }
    }

    private fun initQuantityIndicator() {
        attributes?.getInteger(
            R.styleable.PageIndicator_quantityIndicators,
            DEFAULT_QUANTITY_INDICATORS
        )?.let {
            setQuantityIndicator(it)
        }
    }

    //endregion

    //region MODIFIERS LIST SELECTORS

    fun setIdResDrawableBackgroundIndicator(@DrawableRes idResDrawableBackgroundIndicator: Int) {
        adapterListIndicator?.setIdResDrawableBackgroundIndicator(idResDrawableBackgroundIndicator)
    }

    fun setIdColorSelectedIndicator(@ColorRes idColorSelected: Int) {
        adapterListIndicator?.setIdColorSelectedIndicator(idColorSelected)
    }

    fun setIdColorUnselectedIndicator(@ColorRes idColorUnselected: Int) {
        adapterListIndicator?.setIdColorUnselectedIndicator(idColorUnselected)
    }

    fun setSizeIndicator(size: Int) {
        adapterListIndicator?.setSizeIndicator(size)
    }

    fun setMarginHorizontalIndicator(marginHorizontal: Int) {
        adapterListIndicator?.setMarginHorizontalIndicator(marginHorizontal)
    }

    fun setQuantityIndicator(quantity: Int, positionSelected: Int = 0) {
        adapterListIndicator?.submitList(SelectorModel.createList(quantity, positionSelected))
    }

    //endregion

    //region FUNCTIONS

    fun changeSelectedIndicator(position: Int) {
        adapterListIndicator?.changeSelectedIndicator(position)
    }

    //endregion

}

private val diffUtil = object : DiffUtil.ItemCallback<SelectorModel>() {
    override fun areItemsTheSame(oldItem: SelectorModel, newItem: SelectorModel) =
        oldItem.id == newItem.id && oldItem.isSelected == newItem.isSelected

    override fun areContentsTheSame(oldItem: SelectorModel, newItem: SelectorModel) =
        oldItem.id == newItem.id && oldItem.isSelected == newItem.isSelected
}


@Suppress("NotifyDataSetChanged")
private class PageIndicatorAdapter(
    @DrawableRes private var idResDrawableBackgroundIndicator: Int,
    @ColorRes private var idColorSelected: Int,
    @ColorRes private var idColorUnselected: Int,
    private var sizeIndicator: Int,
    private var marginHorizontalIndicator: Int,
) : ListAdapter<SelectorModel, PageIndicatorAdapter.ViewHolder>(diffUtil) {

    companion object {
        private const val INIT_VALUE_INDEX_SELECTED = -1
    }

    private var indexSelected = INIT_VALUE_INDEX_SELECTED

    fun setIdResDrawableBackgroundIndicator(@DrawableRes idResDrawableBackgroundIndicator: Int) {
        this.idResDrawableBackgroundIndicator = idResDrawableBackgroundIndicator
        notifyDataSetChanged()
    }

    fun setIdColorSelectedIndicator(@ColorRes idColorSelected: Int) {
        this.idColorSelected = idColorSelected
        notifyDataSetChanged()
    }

    fun setIdColorUnselectedIndicator(@ColorRes idColorUnselected: Int) {
        this.idColorUnselected = idColorUnselected
        notifyDataSetChanged()
    }

    fun setSizeIndicator(sizeIndicator: Int) {
        this.sizeIndicator = sizeIndicator
        notifyDataSetChanged()
    }

    fun setMarginHorizontalIndicator(marginHorizontal: Int) {
        this.marginHorizontalIndicator = marginHorizontal
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ViewIndicatorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            model: SelectorModel,
            @DrawableRes idResFormatIndicator: Int,
            @ColorRes idColorSelected: Int,
            @ColorRes idColorUnselected: Int,
            sizeIndicator: Int,
            marginHorizontalIndicator: Int,
        ) {
            with(binding.viewIndicatorLabel) {
                setMarginHorizontalAndSize(marginHorizontalIndicator, sizeIndicator)
                setBackgroundResource(idResFormatIndicator)
                setColor(if (model.isSelected) idColorSelected else idColorUnselected)
            }
        }

        private fun TextView.setColor(isSelected: Int) {
            (background as? GradientDrawable)?.color = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    isSelected
                )
            )
        }

        private fun TextView.setMarginHorizontalAndSize(
            marginHorizontalIndicator: Int,
            sizeIndicator: Int
        ) {
            (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
                with(it) {
                    marginEnd = marginHorizontalIndicator
                    marginStart = marginHorizontalIndicator
                    height = sizeIndicator
                    width = sizeIndicator
                }
            }
        }

    }

    fun changeSelectedIndicator(position: Int) {
        if (currentList.size > position) {
            if (indexSelected != INIT_VALUE_INDEX_SELECTED) {
                currentList[indexSelected].isSelected = false
            }
            currentList[position].isSelected = true
            indexSelected = position
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewIndicatorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            currentList[position],
            idResDrawableBackgroundIndicator,
            idColorSelected,
            idColorUnselected,
            sizeIndicator,
            marginHorizontalIndicator,
        )
    }

}