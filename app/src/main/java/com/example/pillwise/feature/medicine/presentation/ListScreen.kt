package com.example.pillwise.feature.medicine.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pillwise.data.local.entities.Medicine
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: MedicineViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val medicines = remember { mutableStateListOf<Medicine>() }

    LaunchedEffect (Unit) {
        val medicineList = viewModel.getAll()
        medicines.addAll(medicineList)
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Medicine List",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Name", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Expiration Date", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Comment", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(medicines) { medicine ->
                MedicineRow(medicine = medicine)
            }
        }
    }
}

@Composable
fun MedicineRow(medicine: Medicine) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(medicine.name, modifier = Modifier.weight(1f))
        Text(medicine.expirationDate, modifier = Modifier.weight(1f))
        Text(medicine.comment ?: "", modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    ListScreen(
        navController = rememberNavController()
    )
}