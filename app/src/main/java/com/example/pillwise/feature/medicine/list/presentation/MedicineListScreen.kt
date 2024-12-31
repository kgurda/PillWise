package com.example.pillwise.feature.medicine.list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun MedicineListScreen(
    navController: NavController,
    viewModel: MedicineListViewModel = hiltViewModel<MedicineListViewModel>(),
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    MedicineListScreen(
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
fun MedicineListScreen(
    uiState: MedicineListUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.medicine_list_page_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.medicine_name_column_title), fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(
                stringResource(R.string.medicine_expiration_date_column_title),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
            Text(stringResource(R.string.medicine_comment_column_title), fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(uiState.medicines) { medicine ->
                MedicineItem(medicine = medicine)
            }
        }
    }
}

@Composable
fun MedicineItem(medicine: Medicine) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(medicine.name, modifier = Modifier.weight(1f))
        Text(medicine.expirationDate, modifier = Modifier.weight(1f))
        Text(medicine.comment ?: stringResource(R.string.medicine_comment_placeholder), modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun MedicineListScreenPreview() {
    MedicineListScreen(
        uiState = MedicineListUiState()
    )
}
