const btnAllTasks = document.getElementById("btnAllTasks")

btnAllTasks.addEventListener("click",() => {
    $( () => {
        console.log("Page is ready.")
        $.ajax({
            url: "http://localhost:3000/task",
            type: "get",
            
            success: (result) => {
                console.log(result)
            },
            error: (error) => {
                console.log("error ", error)
            }
        })
    })
})

