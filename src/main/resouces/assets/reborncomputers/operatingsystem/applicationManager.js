var Applications = [];

function register(name, func) {
    Applications.push({name:name, func:func})
}

function findApplication(name){
    Applications.forEach(function (app) {
        if(app.name === name){
            return app
        }
    })
}