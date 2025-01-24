package com.example.pillwise.feature.medicine.list.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.pillwise.R
import com.example.pillwise.data.local.entities.Medicine
import com.example.pillwise.feature.medicine.list.presentation.model.MedicineListUiState
import com.example.pillwise.feature.medicine.presentation.BitmapConverter
import com.example.pillwise.navigation.routes.MedicineCreationRoute

@Composable
fun MedicineListScreen(
    navController: NavController,
    viewModel: MedicineListViewModel = hiltViewModel<MedicineListViewModel>(),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    MedicineListScreen(
        uiState = uiState,
        onAddButtonClick = { navController.navigate(MedicineCreationRoute) },
        deleteMedicine = { id: Long -> viewModel.deleteItem(id) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineListScreen(
    uiState: MedicineListUiState,
    onAddButtonClick: () -> Unit,
    deleteMedicine: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.medicine_list_page_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddButtonClick() },
                shape = CircleShape,
                modifier = modifier.padding(8.dp)
            ) {
                Icon(Icons.Filled.Add, stringResource(R.string.create_medicine_button))
            }
        }
    ) { padding ->
        Column(
            modifier =
            modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.medicines) { medicine ->
                    DismissibleCard(medicine = medicine, deleteMedicine = deleteMedicine)
                }
            }
        }
    }
}

@Composable
fun DismissibleCard(
    medicine: Medicine,
    deleteMedicine: (id: Long) -> Unit
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    val dismissState =
        rememberSwipeToDismissBoxState(
            confirmValueChange = { dismissValue ->
                if (dismissValue == SwipeToDismissBoxValue.StartToEnd) {
                    showDialog = true
                    return@rememberSwipeToDismissBoxState false
                } else {
                    return@rememberSwipeToDismissBoxState false
                }
            },
            positionalThreshold = { it * .25f }
        )
    if (showDialog) {
        ConfirmationBox(
            hideDialog = { showDialog = false },
            medicine = medicine,
            deleteMedicine = deleteMedicine
        )
    }
    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier,
        enableDismissFromEndToStart = false,
        backgroundContent = { DismissBackground(dismissState) },
        content = {
            MedicineCard(medicine)
        }
    )
}

@Composable
fun ConfirmationBox(
    hideDialog: () -> Unit,
    medicine: Medicine,
    deleteMedicine: (id: Long) -> Unit
) {
    AlertDialog(
        onDismissRequest = hideDialog,
        title = { Text(text = stringResource(R.string.confirm_delete_title)) },
        text = { Text(text = stringResource(R.string.confirm_delete_message, medicine.name)) },
        confirmButton = {
            TextButton(onClick = {
                deleteMedicine(medicine.id)
                hideDialog
            }) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = hideDialog) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color =
        when (dismissState.dismissDirection) {
            SwipeToDismissBoxValue.StartToEnd -> colorResource(R.color.red_delete)
            SwipeToDismissBoxValue.EndToStart -> Color.Transparent
            SwipeToDismissBoxValue.Settled -> Color.Transparent
        }

    Row(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color)
                .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_content_description)
        )
    }
}

@Composable
fun MedicineCard(medicine: Medicine) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { println() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageField(medicine.image?.let { BitmapConverter.convertByteArrayToBitmap(it) })
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = medicine.name, fontSize = 28.sp)
                Text(medicine.comment ?: stringResource(R.string.medicine_comment_placeholder), fontSize = 14.sp)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.expiration_date_button),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = medicine.expirationDate,
                        fontSize = 12.sp
                    )
                }
            }
            IconButton(onClick = { println() }) {
                Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit_content_description))
            }
        }
    }
}

@Composable
fun ImageField(capturedImage: Bitmap?) {
    val painter =
        if (capturedImage != null) {
            BitmapPainter(capturedImage.asImageBitmap())
        } else {
            painterResource(id = R.drawable.medicine_placeholder)
        }

    Image(
        painter = painter,
        contentDescription = stringResource(R.string.image_content_description),
        modifier =
            Modifier
                .size(68.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
    )
}

@Preview
@Composable
private fun MedicineListScreenPreview() {
    MedicineListScreen(
        uiState =
            MedicineListUiState(
                medicines =
                    listOf(
                        Medicine(
                            id = 1,
                            name = "test",
                            expirationDate = "2025-01-01",
                            comment = "test",
                            image = null
                        ),
                        Medicine(
                            id = 2,
                            name = "test 2",
                            expirationDate = "2025-01-02",
                            comment = "test",
                            image = null
                        ),
                        Medicine(
                            id = 3,
                            name = "test 3",
                            expirationDate = "2025-01-10",
                            comment = "test",
                            image = null
                        )
                    )
            ),
        onAddButtonClick = {},
        deleteMedicine = {}
    )
}
