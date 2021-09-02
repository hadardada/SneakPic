var notiNumber =0;
const refreshRate = 3000; //milliseconds
const notifisBadge = document.getElementById('badge');

window.addEventListener('load', () => {
//The users list is refreshed automatically every 3 seconds
    setInterval(fetchNotificationsCounter, refreshRate);
});

async function fetchNotificationsCounter() {
    const response = await fetch("/user/number-of-notifications", {
        method: "get",
    })
   response.text().then(function (text){
       notiNumber = text;
    });

    if (notiNumber == 0){ // no new unwatched notifications
        notifisBadge.style.display = "none";
    }

    else{ // there are new notifications
        notifisBadge.textContent = notiNumber;
        notifisBadge.style.display = "block";
    }
}