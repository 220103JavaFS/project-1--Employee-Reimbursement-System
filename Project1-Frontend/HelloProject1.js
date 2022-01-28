let loginBtn = document.getElementById("loginBtn");
let request_table = document.getElementById("request_table");
let login_div = document.getElementById("login_div");
/*
let username = document.getElementById("username");
let password = document.getElementById("password");
*/
const url = "http://localhost:8080/";

loginBtn.addEventListener("click", loginFunc);

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
        login_div.style.display = "none";
        request_table.style.display = "table";
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
    let request = {
      requestAmount: document.getElementById("homeName").value,
      requestType: document.getElementById("homeStreetNum").value,
      requestDescription: document.getElementById("homeStreetName").value,
    }

    let response = await fetch(url+"home", {
        method:"POST",
        body:JSON.stringify(home),
        credentials:"include"
      })
    
      if(response.status===201){
        getAllHomes();
        console.log("Home added successfully");
      }else{
        console.log("Problem encountered when adding Home.");
      }
}