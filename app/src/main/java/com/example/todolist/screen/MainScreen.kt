package com.example.todolist.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.todolist.R
import com.example.todolist.data.Todo
import com.example.todolist.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MainScreen(vm:TodoViewModel) {
val todoList by vm.todoList.observeAsState()

    // MutableState to hold the selected TODO item
    val selectedTodo = remember { mutableStateOf<Todo?>(null) }

    val showDialog = remember {
        mutableStateOf(false)
    }
    val onFabClick:()->Unit={
        showDialog.value = true
    }
    val onDismiss:()->Unit={
        showDialog.value = false
    }
    val onAddTodo:(String)->Unit={
    vm.addTodo(it)
        showDialog.value = false
    }
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(8.dp)) {

        todoList?.let {
            LazyColumn(content = {
                itemsIndexed(it){ index: Int, item: Todo ->
                    TodoItem(item = item, onDelete = {
                        vm.deleteTodo(item.id)
                    },
                // Pass the selected TODO item to onEdit
                onEdit = { todo -> selectedTodo.value = todo })

                }
            })
        }?: Text(text = "No items yet")

    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(25.dp)
        , verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End) {

            FAB(showDialog = showDialog.value,
                onFabClick = onFabClick ,
                onDismiss =  onDismiss ,
                onAddTodo = onAddTodo)

    }

    // Show the edit dialog when a TODO item is selected
    selectedTodo.value?.let { todo ->
        EditTodoDialog(
            todo = todo,
            onDismiss = { selectedTodo.value = null }, // Reset selectedTodo when dialog dismissed
            onUpdate = { updatedTodo ->
                vm.updateTodo(updatedTodo)
                selectedTodo.value = null // Reset selectedTodo after updating
            }
        )
    }
}




@Composable
fun TodoItem(item:Todo,onDelete :()->Unit, onEdit: (Todo) -> Unit){
    val backgroundColor = if (isSystemInDarkTheme()) {
        Color.DarkGray // Use dark gray for dark mode
    } else {
        Color.LightGray // Use light gray for light mode
    }

    Row(modifier = Modifier
    .fillMaxWidth()
    .padding(8.dp)
    .clip(RoundedCornerShape(16.dp))
    .background(backgroundColor)
    .padding(10.dp).clickable {
        onEdit(item)
    }, verticalAlignment = Alignment.CenterVertically) {
    Column(modifier = Modifier.weight(1f)) {
        Text(text = SimpleDateFormat("HH:mm:aa,dd/mm", Locale.ENGLISH).format(item.date), fontSize = 13.sp
        )
        Text(text = item.title, fontSize = 17.sp)
    }
    IconButton(onClick =
        onDelete) {
        Icon(painter = painterResource(id = R.drawable.baseline_delete_24), contentDescription = "Delete")
    }

}
}
@Composable
fun FAB(
    showDialog: Boolean,
    onFabClick:() -> Unit,
    onDismiss:() -> Unit,
    onAddTodo: (String) -> Unit
){

    val addTodo = remember{
        mutableStateOf("")
    }
    if (showDialog){
        AlertDialog(onDismissRequest = { onDismiss.invoke()
            addTodo.value=""},
            confirmButton = {
                Button(onClick = { onAddTodo(addTodo.value)
                    addTodo.value = ""
                }) {
                    Text(text = "Add Todo")
                }
            },
            title = { Text(text = "Enter Todo")},
            text = {
                OutlinedTextField(value = addTodo.value,
                    onValueChange = {
                        addTodo.value = it

                    }
                )
            })

    }else{
        FloatingActionButton(
            onClick =  onFabClick ,
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            shape = CircleShape,
            modifier = Modifier.padding(bottom = 40.dp)
        ) {
            Icon(imageVector = Icons.Rounded.Add,
                contentDescription = null,
                tint = Color.White
            )
        }
    }





}
@Composable
fun EditTodoDialog(todo: Todo, onDismiss: () -> Unit, onUpdate: (Todo) -> Unit) {
    val (title, setTitle) = remember { mutableStateOf(todo.title) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onUpdate(todo.copy(title = title))
            }) {
                Text(text = "Update")
            }
        },
        title = { Text(text = "Edit Todo") },
        text = {
            OutlinedTextField(
                value = title,
                onValueChange = { setTitle(it) }
            )
        }
    )
}