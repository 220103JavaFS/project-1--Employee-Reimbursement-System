let loginBtn = document.getElementById("loginBtn");
let loginDiv = document.getElementById("login_div");
let requestFormBtn = document.getElementById("getRequestForm");
let newRequestDiv = document.getElementById("newRequestDiv");
let submitRequestBtn = document.getElementById("submitRequestBtn");

let wholeTableReimb = document.getElementById("tableReimbursements");
let reimbHead = document.getElementById("reimbHead");
let tableAllReimb = document.getElementById("tableReimb");
let buttonShowAll = document.getElementById("reimbBtn");
let requestStatus = document.getElementById("reqStatus");

let aprvForm = document.getElementById("approvalForm");
let aprvBtn = document.getElementById("aprvBtn");
let aprvText = document.getElementById("requestID");
let aprvSelect = document.getElementById("selectAction");

// tableAllReimb.style.display = "none";
// buttonShowAll.style.display = "none";
// requestStatus.style.display = "none";

const url = "http://localhost:8080/";

loginBtn.addEventListener("click", loginFunc);
requestFormBtn.addEventListener("click", createRequestFunc);
submitRequestBtn.addEventListener("click", submitRequest);
buttonShowAll.addEventListener("click", getRequests);
aprvBtn.addEventListener("click",approveRequest);

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
        document.cookie = response.json();
        requestFormBtn.style.display = "inline";
        loginDiv.style.display = "none";

        buttonShowAll.style.display = "inline";
        requestStatus.style.display = "inline";
        wholeTableReimb.style.display = "table";
        aprvForm.style.display = "block";
    }else{
        console.log("Login unsuccessful");
    }
}

async function approveRequest(){
  let request = {
    resolveChoice: aprvSelect.value,
    requestID: parseInt(aprvText.value)
  }

  let response = await fetch(url+"resolveRequest", {
      method:"POST",
      body:JSON.stringify(request),
      credentials:"include"
    })
  
  if(response.status===200){
    console.log("Request approved/denied successfully");
  }else{
    console.log("Problem encountered when approving/denying the request.");
  }

}

// async function logoutFunc(){
//     let username = document.getElementById("username_input").value;
//     let password = document.getElementById("password_input").value;
//     let response = await fetch(url + "logout", 
//     {
//         method:"POST",
//         body:JSON.stringify({
//             username: username,
//             password: password,
//             credentials: "include"
//         })
//     });

//     if (response.status === 200){
//         login_div.style.display = "block";
//         request_table.style.display = "none";
//     }else{
//         console.log("Logout unsuccessful");
//     }
// }
// *******************************************************************************************************
// async function getRequests(){
//   let endPoint = url;
  
//   if(requestStatus.value == "pending"){
//     endPoint = endPoint+ "getByStatus/pending";
//   }
//   else if(requestStatus.value == "all"){
//     endPoint = endPoint+ "getAllRequests";
//   }
//   else if(requestStatus.value == "approved"){
//     endPoint = endPoint+ "getByStatus/approved";
//   }
//   else if(requestStatus.value == "denied"){
//     endPoint = endPoint+ "getByStatus/denied";
//   }
//   else {
//     console.log("Incorrect request status");
//   }

//     let response = await fetch(endPoint, {
//       credentials:"include"
//     });
  

//   if(response.status===200){
//     let records = await response.json();
    
//     console.log(records);
    
//     populateRequests(records);
    
    
//   } else{
//     console.log("There was an error getting your requests.")
//   }
     
// }

// function populateRequests(requests){
    
//     tableAllReimb.innerHTML = "";
//     for (let request of requests){
            
//         let row = document.createElement("tr");
//         for (let data in request){
//             let td = document.createElement("td");
//             td.innerText = request[data];
//             row.appendChild(td);
//         }
        
//       }
//       tableAllReimb.appendChild(row);
// }

async function submitRequest(){
    // const timeElapsed = Date.now();
    // const today = new Date(timeElapsed);
    let request = {
      amount: document.getElementById("requestAmount").value,
      type: document.getElementById("requestType").value,
      description: document.getElementById("requestDescription").value,
      // submitted: today.toISOString()
    }

    let response = await fetch(url+"addRequest", {
        method:"POST",
        body:JSON.stringify(request),
        credentials:"include"
      })
    
    if(response.status===200){
      console.log("Request added successfully");
    }else{
      console.log("Problem encountered when adding the request.");
    }
}

function createRequestFunc(){
    newRequestDiv.style.display = "block";
}

async function getRequests(){
  let endPoint = url;
  
  if(requestStatus.value == "pending"){
    endPoint = endPoint+ "getByStatus/Pending";
  }
  else if(requestStatus.value == "all"){
    endPoint = endPoint+ "getAllRequests";
  }
  else if(requestStatus.value == "approved"){
    endPoint = endPoint+ "getByStatus/Approved";
  }
  else if(requestStatus.value == "denied"){
    endPoint = endPoint+ "getByStatus/Denied";
  }
  else {
    console.log("Incorrect request status");
  }

    let response = await fetch(endPoint, {
      credentials:"include"
    });
  

  if(response.status===200){
    let records = await response.json();
    populateRequests(records);
  } else{
    console.log("There was an error getting your requests.")
  }
     
}

function populateRequests(requests){
    tableAllReimb.innerHTML = "";
    for (let request of requests){
        let row = document.createElement("tr");
        for (let data in request){
            let td = document.createElement("td");
            td.innerText = request[data];
            row.appendChild(td);
        }
        tableAllReimb.appendChild(row);
    }
}