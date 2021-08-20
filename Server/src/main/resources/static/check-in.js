
const checkInButton = document.querySelector('#checkInButton');
locationButton.addEventListener("click", sendPosition())
async function sendPosition(){
    var pos = getLocation();
    const response = await fetch('/check-in', {
        method: 'post',
        headers: new Headers({
            'Content-Type': 'application/json;charset=utf-8'
        }),
        body: JSON.stringify(pos)
    });
}
function getLocation() {
    var x = document.getElementById("demo");
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude,
                    time: position.timestamp,
                    duration: 0,
                    manually: false // meaning it's a check in on actual time and not retroactively
                };
                // infoWindow.setPosition(pos);
                // infoWindow.setContent("Location found.");
                // infoWindow.open(map);
                // map.setCenter(pos);
         //   },
          //  () => {
            //    handleLocationError(true, infoWindow, map.getCenter());
            }
        );return pos;
    } else {
        x.innerHTML = "Geolocation is not supported by this browser.";
    }
}