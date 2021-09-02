const msgFromServer =document.querySelector("#msgFromServer");
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const badCredentials = urlParams.get('error');
const logout = urlParams.get('logout');
if (badCredentials==""){
    msgFromServer.style.display = "block";
    msgFromServer.textContent = "Username or password is incorrect"
}
else if (logout==""){
    msgFromServer.style.display = "block";
    msgFromServer.textContent = "You have been logged out of SneakPic"
}


