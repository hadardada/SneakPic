const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const albumId = urlParams.get('albumId');

window.addEventListener('DOMContentLoaded', injectParameters);