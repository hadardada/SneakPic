
const checkInButton = document.querySelector('#send-location');
var map, infoWindow;
var pos;
const pageContent = document.getElementById('page-content');
const msgSent = document.getElementById('demo');
function initMap(){
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                 pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude,
                     toTime:  position.timestamp, // same time for both from and to since it's a check-in
                     fromTime: position.timestamp,
                     manually: false, // meaning it's a check in on actual time and not retroactively
                };
                map = new google.maps.Map(document.getElementById("map"), {
                    center: { lat: pos.lat, lng: pos.lng },
                    zoom: 15,
                })
                new google.maps.Marker({
                    position: { lat: pos.lat, lng: pos.lng },
                    map,})
                ;
            });

    }
    else
        window.alert("Geolocation is not supported by this browser. Please allow location service");
    //TODO check is it works on mobile correctly
}

checkInButton.addEventListener("click", async function sendPosition(){
    const response =  await fetch('/user/check-in', {
        method: 'post',
        headers: new Headers({
            'Content-Type': 'application/json;charset=utf-8'
        }),
        body: JSON.stringify(pos)
    });

    if (response.ok){
        pageContent.style.display="none";
        msgSent.innerText = "Your location has been received.";
    }
}
);



