
function createStarsIconsForRating(span, rate){
    if (rate == 0) {
        span.innerText = "No rating yet";
        return;
    }
    var emptyStars;
    var fullStars = (rate|0); // get number of full star, the first one is always full
    //(minimum rate possible is 1)
    for (i = 0; i<fullStars; i++)
    {
        var fullStar = document.createElement('i');
        fullStar.className="fas fa-star";
        span.appendChild(fullStar);
    }
    if (rate-fullStars != 0) // if rate is not a whole number
    {
        var halfStar = document.createElement('i');
        halfStar.className="fas fa-star-half-alt";
        span.appendChild(halfStar);
        emptyStars = FIVE_STARS - 1 - fullStars;
    }
    else
        emptyStars = FIVE_STARS -fullStars;
    for (i=0;i <emptyStars;i++){
        var emptyStarEl = document.createElement('i');
        emptyStarEl.className = "far fa-star";
        span.appendChild(emptyStarEl);
    }
    return;
}