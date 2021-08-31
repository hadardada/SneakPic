var pos;
var fromSelect = document.querySelector('#from');
var toSelect = document.querySelector('#to');
var sendLocation = document.querySelector('#send-location');

function initMap() {
    const dizengoff = { lat: 32.07830818015251, lng: 34.774092798618 };
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 13,
        center: dizengoff,
    });
    map.addListener("dblclick", (e) => {
        placeMarkerAndPanTo(e.latLng, map);
        pos = {
            lat: e.latLng.lat(),
            lng: e.latLng.lng(),
            from: 0,
            to:  0, // same time for both from and to since it's a check-in
            manually: true, // meaning it's a check in on actual time and not retroactively
        }
    });
}

function placeMarkerAndPanTo(latLng, map) {
    new google.maps.Marker({
        position: latLng,
        map: map,
    });
    map.panTo(latLng);
}

function fromTimeHandler(event){
    pos.from = event.target.value;
}

function toTimeHandler(event){
    pos.to = event.target.value;
}

sendLocation.addEventListener("click", function sendPosition(){
        const response =  fetch('/user/check-in', {
            method: 'post',
            headers: new Headers({
                'Content-Type': 'application/json;charset=utf-8'
            }),
            body: JSON.stringify(pos)
        });
    }
);

