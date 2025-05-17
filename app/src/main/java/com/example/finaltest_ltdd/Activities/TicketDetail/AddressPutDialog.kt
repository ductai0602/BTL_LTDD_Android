package com.example.finaltest_ltdd.Activities.TicketDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.finaltest_ltdd.R

@Composable
fun AddressInputDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val cities = listOf("TP. Hồ Chí Minh", "Hà Nội", "Đà Nẵng")
    val districtsByCity = mapOf(
        "TP. Hồ Chí Minh" to listOf("Quận 1", "Quận 3", "Quận 7"),
        "Hà Nội" to listOf("Ba Đình", "Hoàn Kiếm", "Cầu Giấy"),
        "Đà Nẵng" to listOf("Hải Châu", "Thanh Khê", "Sơn Trà")
    )
    val wardsByDistrict = mapOf(
        "Quận 1" to listOf("Phường Bến Nghé", "Phường Bến Thành", "Phường Đa Kao"),
        "Quận 3" to listOf("Phường 7", "Phường 8", "Phường Võ Thị Sáu"),
        "Quận 7" to listOf("Phường Tân Phú", "Phường Tân Thuận Đông", "Phường Phú Mỹ"),
        "Ba Đình" to listOf("Phường Giảng Võ", "Phường Kim Mã", "Phường Ngọc Khánh"),
        "Hoàn Kiếm" to listOf("Phường Hàng Bạc", "Phường Hàng Bông", "Phường Tràng Tiền"),
        "Cầu Giấy" to listOf("Phường Dịch Vọng", "Phường Mai Dịch", "Phường Nghĩa Đô"),
        "Hải Châu" to listOf("Phường Hải Châu I", "Phường Thanh Bình", "Phường Thạch Thang"),
        "Thanh Khê" to listOf("Phường An Khê", "Phường Hòa Khê", "Phường Thanh Khê Đông"),
        "Sơn Trà" to listOf("Phường An Hải Bắc", "Phường An Hải Tây", "Phường Mân Thái")
    )

    var selectedCity by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf("") }
    var selectedWard by remember { mutableStateOf("") }
    var houseNumber by remember { mutableStateOf("") }
    var cityExpanded by remember { mutableStateOf(false) }
    var districtExpanded by remember { mutableStateOf(false) }
    var wardExpanded by remember { mutableStateOf(false) }

    val availableDistricts = districtsByCity[selectedCity] ?: emptyList()
    val availableWards = wardsByDistrict[selectedDistrict] ?: emptyList()

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .background(
                    color = colorResource(R.color.lightPurple),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Enter Delivery Address",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownField(
                    label = "Thành phố",
                    value = selectedCity,
                    items = cities,
                    expanded = cityExpanded,
                    onExpandedChange = { cityExpanded = it },
                    onItemSelected = { city ->
                        selectedCity = city
                        selectedDistrict = ""
                        selectedWard = ""
                        cityExpanded = false
                    },
                    enabled = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                DropdownField(
                    label = "Quận",
                    value = selectedDistrict,
                    items = availableDistricts,
                    expanded = districtExpanded,
                    onExpandedChange = { districtExpanded = it },
                    onItemSelected = { district ->
                        selectedDistrict = district
                        selectedWard = ""
                        districtExpanded = false
                    },
                    enabled = selectedCity.isNotEmpty()
                )

                Spacer(modifier = Modifier.height(8.dp))

                DropdownField(
                    label = "Phường",
                    value = selectedWard,
                    items = availableWards,
                    expanded = wardExpanded,
                    onExpandedChange = { wardExpanded = it },
                    onItemSelected = { ward ->
                        selectedWard = ward
                        wardExpanded = false
                    },
                    enabled = selectedDistrict.isNotEmpty()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = houseNumber,
                    onValueChange = { houseNumber = it },
                    label = { Text("Số nhà, Tên đường") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        disabledTextColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedLabelColor = colorResource(R.color.darkPurple2),
                        unfocusedLabelColor = Color.Gray,
                        disabledLabelColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 16.sp,
                        color = colorResource(R.color.darkPurple2),
                        modifier = Modifier
                            .clickable { onDismiss() }
                            .padding(8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorResource(R.color.darkPurple2),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                if (selectedCity.isNotBlank() &&
                                    selectedDistrict.isNotBlank() &&
                                    selectedWard.isNotBlank() &&
                                    houseNumber.isNotBlank()
                                ) {
                                    val fullAddress = "$houseNumber, $selectedWard, $selectedDistrict, $selectedCity"
                                    onConfirm(fullAddress)
                                    onDismiss()
                                }
                            }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Confirm",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownField(
    label: String,
    value: String,
    items: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onItemSelected: (String) -> Unit,
    enabled: Boolean
) {
    Box {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp)),
            enabled = enabled,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = if (enabled) colorResource(R.color.darkPurple2) else Color.Gray
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Gray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedLabelColor = colorResource(R.color.darkPurple2),
                unfocusedLabelColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(enabled = enabled) { onExpandedChange(true) }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item, color = Color.Black) },
                    onClick = {
                        onItemSelected(item)
                        onExpandedChange(false)
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}