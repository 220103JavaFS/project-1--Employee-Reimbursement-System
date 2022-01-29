let loginBtn = document.getElementById("loginBtn");
let requestTable = document.getElementById("requestTable");
let loginDiv = document.getElementById("login_div");
let requestFormBtn = document.getElementById("getRequestForm");
let newRequestDiv = document.getElementById("newRequestDiv");
/*
let username = document.getElementById("username");
let password = document.getElementById("password");
*/
const url = "http://localhost:8080/";

loginBtn.addEventListener("click", loginFunc);
requestFormBtn.addEventListener("click", createRequestFunc);

async function loginFunc(){
    let user = {
        username: document.getElementById("username_input").value,
        password: document.getElementById("password_input").value
      }

      let response = await fetch(
        url+"login",
        {
          method : "POST",
          
          body : JSON.stringify(user),
          credentials: "include"
        }
      );

    if (response.status === 200){
        console.log("The login post request succeeded");
        requestFormBtn.style.display = "inline";
        loginDiv.style.display = "none";
        requestTable.style.display = "table";
    }else{
        console.log("Login unsuccessful");
    }
}

async function logoutFunc(){
    let username = document.getElementById("username_input").value;
    let password = document.getElementById("password_input").value;
    let response = await fetch(url + "logout", 
    {
        method:"POST",
        body:JSON.stringify({
            username: username,
            password: password,
            credentials: "include"
        })
    });

    if (response.status === 200){
        login_div.style.display = "block";
        request_table.style.display = "none";
    }else{
        console.log("Logout unsuccessful");
    }
}

async function getRequests(){
    let response = await fetch(url+"requests", {
      credentials:"include"
    });
  
    if(response.status===200){
      let requests = await response.json();
      populateRequests(requests);
    } else{
      console.log("There was an error getting your homes.")
    }
     
}

function populateRequests(requests){
    homeTable.innerHTML = "";
    for (let request of requests){
        let row = document.createElement("tr");
        for (let data in request){
            let td = document.createElement("td");
            td.innerText = request[data];
            row.appendChild(td);
        }
        request_table.appendChild(row);
    }
}

async function submitRequest(){
    const timeElapsed = Date.now();
    const today = new Date(timeElapsed);
    let request = {
      amount: document.getElementById("requestAmount").value,
      type: document.getElementById("requestType").value,
      description: document.getElementById("requestDescription").value,
      submitted: today.toLocaleDateString()
    }

    let response = await fetch(url+"addRequest", {
        method:"POST",
        body:JSON.stringify(request),
        credentials:"include"
      })
    
      if(response.status===200){
        getAllHomes();
        console.log("Request added successfully");
      }else{
        console.log("Problem encountered when adding the request.");
      }
}

function createRequestFunc(){
    requestTable.style.display = "none";
    newRequestDiv.style.display = "block";
}