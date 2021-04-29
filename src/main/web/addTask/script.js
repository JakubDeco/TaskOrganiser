const btnAddTask = document.getElementById('btnAddTask')

btnAddTask.addEventListener('click', () => {
    const taskName = document.getElementById('name').value
    const priority = document.getElementById('priority').value
    const timeEst = document.getElementById('timeEstimate').value
    
    const newTask = {taskName, priority, timeEst}

    $.ajax({
        url: "http://localhost:3000/task/new",
        type: "post",
        dataType: "json",
        contentType: "application/json",
        data: newTask,
        success: (result) => {
            console.log(result)
        },
        error: (err) => {
            console.log("error occured: ", err)
        }
    })
})
