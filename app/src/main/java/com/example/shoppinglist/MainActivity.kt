package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.graphics.Color

data class ShoppingItem(
    val name: String,
    val quantity: String,
    var isChecked: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {
                ShoppingListTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ShoppingListApp()
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingListApp() {
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    val shoppingItems = remember { mutableStateListOf<ShoppingItem>() }
    val brown = Color(0xFF654846)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "My Shopping List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = brown
        )

        Spacer(modifier = Modifier.height(16.dp))

        // TextField for Item Name
        OutlinedTextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("What to buy?") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = brown,
                unfocusedBorderColor = brown,
                focusedLabelColor = brown,
                cursorColor = brown
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // TextField for Quantity
        OutlinedTextField(
            value = itemQuantity,
            onValueChange = { itemQuantity = it },
            label = { Text("Quantity") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = brown,
                unfocusedBorderColor = brown,
                focusedLabelColor = brown,
                cursorColor = brown
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Button
        Button(
            onClick = {
                if (itemName.isNotBlank() && itemQuantity.isNotBlank()) {
                    shoppingItems.add(ShoppingItem(name = itemName, quantity = itemQuantity))
                    itemName = ""
                    itemQuantity = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = brown,
                contentColor = Color.White
            )
        ) {
            Text("Add This Item!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn
        LazyColumn {
            itemsIndexed(shoppingItems) { index, item ->
                ShoppingListItem(
                    item = item,
                    onCheckedChange = { isChecked ->
                        shoppingItems[index] = item.copy(isChecked = isChecked)
                    }
                )
                Divider()
            }
        }
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                if (item.isChecked) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                else MaterialTheme.colorScheme.background
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${item.name} (${item.quantity})",
            style = MaterialTheme.typography.bodyLarge,
            textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
        )
    }
}


//@Preview(showBackground = true)
//@Composable
