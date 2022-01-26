let loginBtn = document.getElementById("loginBtn");
/*
let username = document.getElementById("username");
let password = document.getElementById("password");
*/
const url = "http://localhost:8080/";

loginBtn.addEventListener("click", loginFunc);

async function loginFunc(){
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let response = await fetch(url + "login", 
    {
        method:"POST",
        body:JSON.stringify({
            username: username,
            password: password,
            credentials: "include"
        })
    });

    if (response.status === 200){
        loginBtn.innerText = "";
    }else{
        console.log("Login unsuccessful");
    }
}

function populateHomes(homes){
    homeTable.innerHTML = "";
    for (let home of homes){
        let row = document.createElement("tr");
        for (let data in home){
            let td = document.createElement("td");
            td.innerText = home[data];
            row.appendChild(td);
        }
        homeTable.appendChild(row);
    }
}