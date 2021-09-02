var pos;
var fromSelect = document.querySelector('#from');
var toSelect = document.querySelector('#to');
var sendLocation = document.querySelector('#send-location');
var postAlbum = document.querySelector('#postAlbumForm');
var currMarker;
var albumPosition;


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
            fromTime: 0,
            toTime:  0, // same time for both from and to since it's a check-in
            manually: true, // meaning it's a check in on actual time and not retroactively
        }
    });
}

function placeMarkerAndPanTo(latLng, map) {
    //delete previous marker if exists
    if (currMarker != null)
        currMarker.setMap(null);

    //create a new marker
    currMarker = new google.maps.Marker({
        position: latLng,
        map: map,
    });
    map.panTo(latLng);
}

function fromTimeHandler(event){
    let d = new Date(event.target.value);
    pos.fromTime = d.getTime();
}

function toTimeHandler(event){
    let d = new Date(event.target.value);
    pos.toTime = d.getTime();
}

function sendPosition(){
        const response =  fetch('/user/check-in', {
            method: 'post',
            headers: new Headers({
                'Content-Type': 'application/json;charset=utf-8'
            }),
            body: JSON.stringify(pos)
        });
    }

async function addPositionToAlbum(event){
    var albumId;
    const nameEl = document.querySelector('#name');
    albumDetails = {
        name : nameEl.value,
        lat: pos.lat,
        lng: pos.lng,
        fromTime: pos.fromTime,
        toTime: pos.toTime,
    }
    event.preventDefault();
    const response = await fetch('/user/load-album', {
        method: 'post',
        headers: new Headers({
            'Content-Type': 'application/json;charset=utf-8'
        }),
        body: JSON.stringify(albumDetails),
        redirect: "follow",
    });
        window.location.href = "/user/load-image";
}







