const btnAddTask = document.getElementById('btnAddTask')

btnAddTask.addEventListener('click', () => {
    const name = document.getElementById('name').value
    const priority = document.getElementById('priority').value
    const timeEstimate = document.getElementById('timeEstimate').value
    
    const newTask = {name, priority, timeEstimate}

    $.ajax({
        url: "http://localhost:3000/task/new",
        type: "post",
        dataType: "application/json",
        data: newTask,
        statusCode: {
            201: (result) => {
                console.log(result.responseText)
            },
            500: (result) => {
                console.log(result.responseText)
            }
        }
    })
})
