package com.palone.planahead.screens.taskEdit.ui.scroller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned

@Composable
fun Scroller(
    modifier: Modifier = Modifier,
    items: Array<Number>,
    selectedItem: Number,
    onNewSelection: (Number) -> Unit,
    itemContent: @Composable() ((item: Number, isSelected: Boolean) -> Unit)
) {
    val state = rememberLazyListState(
        initialFirstVisibleItemIndex = items.indexOf(selectedItem),
        initialFirstVisibleItemScrollOffset = 0 // doesn't really matter
    )
    val singleContentHeight = remember { mutableStateOf(0) }


    Box(modifier) {
        Box(
            modifier = Modifier
                .alpha(0f)
                .onGloballyPositioned { singleContentHeight.value = it.size.height }) {
            itemContent(0, false)
        }

        LazyColumn(
            state = state,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(1) {
                Box( // spacer
                    modifier = Modifier
                        .alpha(0f)
                ) {
                    itemContent(0, false)
                }
            }
            items(items.toList(), key = { item -> item }) { item ->
                val isSelected = selectedItem == item
                itemContent(item, isSelected)
            }
            items(1) {
                Box( // spacer
                    modifier = Modifier.alpha(0f)
                ) {
                    itemContent(0, false)
                }
            }
        }

        LaunchedEffect(state.isScrollInProgress) {// new item selector
            val middleItem = (state.layoutInfo.visibleItemsInfo.size / 2)
            if (state.layoutInfo.visibleItemsInfo.isNotEmpty()) {
                val middleItemValue =
                    state.layoutInfo.visibleItemsInfo[middleItem].key as? Number
                if (middleItemValue != null && !state.isScrollInProgress) {
                    onNewSelection(middleItemValue)
                }
            }
        }
        LaunchedEffect(selectedItem) {//animator
            val index =
                if (items.indexOf(selectedItem) + 1 < 0) 0 else items.indexOf(selectedItem) + 1 // animator
            if (!state.isScrollInProgress) {
                state.animateScrollToItem(
                    index,
                    scrollOffset = -((state.layoutInfo.viewportSize.height - singleContentHeight.value) / 2)
                )
            }
        }
    }
}