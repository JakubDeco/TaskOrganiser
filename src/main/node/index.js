const { response } = require('express')
const express = require('express')
const mongodb = require('mongodb')
const { MongoClient } = require('mongodb')
const url = require('url')

const connectionToURL = 'mongodb://localhost:27017'
const dbsName = 'taskOrganiser'

const app = express()
const client = new MongoClient(connectionToURL, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
});

app.use(express.urlencoded({
    extended: true
}))
app.use(express.json())

app.use(function (req,res,next){
    res.setHeader('Access-Control-Allow-Origin','*')

    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');

    next()
})



app.get('/task', (req, res) => {

    res.setHeader('Access-Control-Allow-Origin','*');

    client.connect((error) => {
        if (error) {
            return console.log("Connection failed")
        }
        console.log('connection sucessfull')

        let filter = {}
        const qDone = req.query.done == true ? true : req.query.done == false ? false : undefined
        const qPriority =
            typeof (req.query.prioriry) === "number"
                && req.query.priority < 4
                && req.query.priority > 0
                && req.query.priority % 1 === 0 ? req.query.priority : undefined

        if (qDone !== undefined || qPriority !== undefined){
            if (qDone !== undefined) filter.done = qDone
            if (qPriority !== undefined) filter.priority = qPriority
        }  
        try {
            client.db(dbsName).collection('task').find(filter).toArray((error, result) => {
                if (error) throw error
                res.status(200).send(result);
            });
            
        } catch (error) {
            res.status(500).send({"errror": "Internal server error."})
        } finally{
            // client.close()
            // console.log("DBS connection closed.")
        }
    });
})

app.get('', (req, res) => {
    res.send('Hello friend!')
})

app.post('/task/new', (req, res) => {

    res.setHeader('Access-Control-Allow-Origin','*');

    const body = req.body
    
    if (typeof (body.name === "string")
        && typeof (body.timeEstimate === "number")
        && typeof (body.priority === "number")
        && body.priority < 4
        && body.priority > 0
        && body.priority % 1 === 0
        && body.timeEstimate > 0) {
        //console.log('am here')

        const newTask = {
            'name': body.name,
            'priority': body.priority,
            'timeEstimate': body.timeEstimate,
            'done': false,
            'date': new Date().toISOString(),
        };

        client.connect((error) => {
            if (error) {
                return console.log("Connection failed")
            } else {
                console.log('connection sucessfull')
                client.db(dbsName).collection('task').insertOne(newTask)
                res.send(newTask)
            }
        })
    } else {
        res.send({ 'error': 'Incorrect request body.' })
    }

})

app.patch('/task/done', (req, res) => {
    const id = req.query._id
    if (id != undefined) {
        // console.log(filter._id)
        // console.log(filter)
        client.connect((error) => {
            if (error) {
                console.log("Connection failed")
                res.status(500).send({ "error": "internal server error" })
            } else {
                console.log('connection sucessfull')

                const filter = { _id: new mongodb.ObjectID(id) }
                const update = { "$set": { "done": true } }
                const options = { returnNewDocument: true }

                client.db(dbsName).collection('task').findOneAndUpdate(filter, update, options).toArray( (err, result) => {
                    if (err) {
                        res.status(400).send({ "error": "Task can not be changed." })
                    } else if (result.matchedCount == 0) {
                        console.log(result)
                        res.status(404).send({ "info": "Task not found." })
                    } else {
                        console.log(result)
                        res.status(200).send({ "ok": "Task was set as completed." })
                    }
                })
            }
        })
    } else {
        res.status(400).send({ "error": "_id parameter required" })
    }

})

app.get('/task/name', (req, res) => {

    const name = req.query.name

    if (name != undefined) {
        
        client.connect((error) => {

            if (error) {

                res.status(500).send({ error: "Internal server error." })
                console.log("Connection failed")

            } else {

                console.log('connection sucessfull')
                client.db(dbsName).collection('task').find({ name: name }).toArray((err, result) => {
                    if (err) {
                        console.log(err)
                        res.status(500).send({ error: "Internal server error." })
                    } else {
                        res.status(200).send(result);
                    } 
                })
            }

        })
    } else {
        res.status(400).send({ error: "name parameter required" })
    }
})


app.get('/about', (req, res) => {
    res.send('<h1>About</h1>')
})

app.get('/author', (req, res) => {
    res.send('Huber Hubec Hubar')
})


app.listen(3000, () => {
    console.log("Server up and running at port 3000")
})
