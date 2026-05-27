package com.example.studentsforms

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black // შავი ფონი მთლიანად აპლიკაციისთვის
                ) {
                    StudentFormScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentFormScreen() {
    val context = LocalContext.current

    // მოთხოვნილი State ცვლადები
    var nameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }

    // ფერები: შავი, თეთრი და წითელი
    val backgroundColor = Color.Black
    val primaryColor = Color(0xFFD32F2F) // წითელი ფერი
    val textColor = Color.White

    // კალენდრის დიალოგი (როგორც ფოტოზეა)
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            dateState = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // სათაური
        Text(
            text = "სტუდენტის რეგისტრაცია",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // სახელი
        OutlinedTextField(
            value = nameState,
            onValueChange = { nameState = it },
            label = { Text("სახელი და გვარი") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = primaryColor.copy(alpha = 0.5f),
                focusedLabelColor = primaryColor,
                unfocusedLabelColor = textColor,
                focusedTextColor = textColor,
                unfocusedTextColor = textColor
            )
        )

        // კალენდრის ველი
        OutlinedTextField(
            value = dateState,
            onValueChange = { },
            label = { Text("აირჩიეთ თარიღი") },
            readOnly = true,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = textColor,
                disabledBorderColor = primaryColor.copy(alpha = 0.5f),
                disabledLabelColor = textColor
            )
        )

        // ელ. ფოსტა
        OutlinedTextField(
            value = emailState,
            onValueChange = { emailState = it },
            label = { Text("ელ. ფოსტა") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = primaryColor.copy(alpha = 0.5f),
                focusedLabelColor = primaryColor,
                unfocusedLabelColor = textColor,
                focusedTextColor = textColor,
                unfocusedTextColor = textColor
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Radio Buttons (მიმართულებები)
        Text(text = "აირჩიე მიმართულება", fontSize = 18.sp, color = textColor)
        val options = listOf("Android", "iOS", "Web","SQL","Design")
        Column {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedOption = option }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = (selectedOption == option),
                        onClick = { selectedOption = option },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = primaryColor,
                            unselectedColor = textColor
                        )
                    )
                    Text(text = option, color = textColor)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Switch
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "ვეთანხმები წესებს და პირობებს",
                modifier = Modifier.weight(1f),
                color = textColor
            )
            Switch(
                checked = isAgreed,
                onCheckedChange = { isAgreed = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = primaryColor,
                    checkedTrackColor = primaryColor.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.DarkGray
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // გაგზავნის ღილაკი
        Button(
            onClick = {
                val isFormValid = nameState.isNotBlank() &&
                        emailState.isNotBlank() &&
                        dateState.isNotBlank() &&
                        selectedOption.isNotBlank() &&
                        isAgreed

                if (isFormValid) {
                    Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
        ) {
            Text(text = "გაგზავნა", fontSize = 18.sp, color = Color.White)
        }
    }
}