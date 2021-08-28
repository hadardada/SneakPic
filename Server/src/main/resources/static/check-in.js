
const checkInButton = document.querySelector('#send-location');
checkInButton.addEventListener("click", sendPosition())
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
        window.alert("Geolocation is not supported by this browser. Please allow location service");
        //TODO check is it works on mobile correctly
    }
}