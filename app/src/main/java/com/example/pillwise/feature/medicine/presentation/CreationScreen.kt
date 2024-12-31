package com.example.pillwise.feature.medicine.presentation

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pillwise.R
import com.example.pillwise.feature.medicine.presentation.model.CreationUiState
import com.example.pillwise.navigation.routes.ListRoute

@Composable
fun CreationScreen(
    navController: NavController,
    viewModel: MedicineViewModel = hiltViewModel<MedicineViewModel>(),
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(uiState.created) {
        if (uiState.created) {
            navController.navigate(ListRoute)
            viewModel.consumeCreatedAction()
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        CreationForm(
            uiState,
            uploadPhoto = { photo -> viewModel.uploadPhoto(photo) },
            updateExpirationDate = { date -> viewModel.updateExpirationDate(date) },
            updateName = { name -> viewModel.updateName(name) },
            updateComment = { comment -> viewModel.updateComment(comment) },
            create = { -> viewModel.create() },
        )
    }
}

@Composable
fun CreationForm(
    uiState: CreationUiState,
    uploadPhoto: (Bitmap) -> Unit,
    updateExpirationDate: (String) -> Unit,
    updateName: (String) -> Unit,
    updateComment: (String) -> Unit,
    create: () -> Unit,
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                uploadPhoto(bitmap)
            }
        }

    DatePicker(
        onDateSelected = { selectedDate ->
            updateExpirationDate(selectedDate)
            showDatePicker = false
        },
        shouldShowDataPicker = showDatePicker,
        onDismiss = { showDatePicker = false },
    )

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = uiState.name,
            onValueChange = { updateName(it) },
            label = { Text(stringResource(R.string.medicine_name_text_field)) },
            modifier = Modifier.fillMaxWidth(),
            isError = !uiState.isNameValid,
        )
        if (!uiState.isNameValid) {
            Text(
                text = stringResource(R.string.medicine_name_validation_message),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        uiState.capturedImage?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = stringResource(R.string.image_field),
                modifier =
                    Modifier
                        .size(200.dp)
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(8.dp)),
            )
        }

        Button(
            onClick = { cameraLauncher.launch(null) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.image_button))
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.expirationDate,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.expiration_date_text_field)) },
            modifier = Modifier.fillMaxWidth(),
            isError = !uiState.isExpirationDateValid,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.expiration_date_button),
                    )
                }
            },
        )
        if (!uiState.isExpirationDateValid) {
            Text(
                text = stringResource(R.string.expiration_date_validation_message),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Comment Field
        TextField(
            value = uiState.comment,
            onValueChange = { updateComment(it) },
            label = { Text(stringResource(R.string.comment_text_field)) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Create Button with Validation
        Button(
            onClick = { create() },
            modifier = Modifier.fillMaxWidth(),
            enabled = (uiState.name.isNotEmpty() && uiState.expirationDate.isNotEmpty()) || uiState.isLoading == true,
        ) {
            Text(stringResource(R.string.create_button))
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Preview
@Composable
private fun CreationScreenPreview() {
    CreationScreen(
        navController = rememberNavController(),
    )
}
