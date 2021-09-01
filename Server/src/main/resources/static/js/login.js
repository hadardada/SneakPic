const msgFromServer =document.getElementsByClassName("msgFromServer");
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const badCredentials = urlParams.get('error');
const logout = urlParams.get('logout');
if (badCredentials==""){
    msgFromServer.style.display = 'block';
}


