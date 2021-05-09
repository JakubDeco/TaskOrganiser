const btnAllTasks = document.getElementById("btnAllTasks")
const uComplete = document.getElementsByClassName("complete-u")


const complete = (element) => {
    $(() => {
        //console.log("clicked complete")
        //console.log(element.value)
        $.ajax({
            url: `http://localhost:3000/task/done?_id=${element.value}`,
            type: "patch",

            statusCode: {
                201: (result) => {
                    
                    //remove complete button and change Done value to true
                    document.getElementById(`${element.value}-done`).textContent = 'true';
                    document.getElementById(element.value).removeChild(element)
                }
            },
            
            error: (error) => {
                console.log("error ", error)
            }
        })
    })
}

btnAllTasks.addEventListener("click", () => {
    $(() => {
        //console.log("Page is ready.")
        $.ajax({
            url: "http://localhost:3000/task",
            type: "get",

            success: (result) => {

                // remove existing content of "tasks-container"
                document.getElementById('tasks-container').innerHTML = ''

                for (var index in result) {
                    const _id = result[index]._id
                    const name = result[index].name
                    const done = result[index].done
                    const priority = result[index].priority
                    const timeEstimate = result[index].timeEstimate

                    let task;
                    if (done){
                        task = 'ID: ' + _id + '</br>NAME: ' + name + `</br>Done: <span id="${_id}-done">${done}</span>`
                        + '</br>PRIORITY: ' + priority + ' ... ' + timeEstimate + 'h'
                    }else{
                        task = 'ID: ' + _id + '</br>NAME: ' + name + `</br>Done: <span id="${_id}-done">${done}</span>`
                            +` <button class="complete-u" onClick="complete(this)" value="${_id}">complete</button>`+ '</br>PRIORITY: ' + priority + ' ... ' + timeEstimate + 'h'
                        }
                        //console.log(task)
                    const div = $(`<div class='task-div' id='${_id}'><div>`).html(task)
                    $("#tasks-container").append(div)
                }
            },
            error: (error) => {
                console.log("error ", error)
            }
        })
    })
})

// uComplete.addEventListener("click", () => {
//     complete()
// })
