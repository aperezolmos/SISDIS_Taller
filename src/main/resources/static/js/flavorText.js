document.addEventListener("DOMContentLoaded", function() {
    // Thymeleaf renderiza el array como JSON
    const flavorTexts = window.flavorTexts || [];
    let idx = 0;
    const flavorTextSpan = document.getElementById("flavorText");
    const prevBtn = document.getElementById("prevFlavor");
    const nextBtn = document.getElementById("nextFlavor");

    if (flavorTexts.length > 1) {
        nextBtn.style.display = "inline-block";
    }

    function updateFlavor() {
        flavorTextSpan.textContent = flavorTexts[idx];
        prevBtn.style.display = idx > 0 ? "inline-block" : "none";
        nextBtn.style.display = idx < flavorTexts.length - 1 ? "inline-block" : "none";
    }

    if (prevBtn && nextBtn && flavorTexts.length > 0) {
        prevBtn.addEventListener("click", function() {
            if (idx > 0) {
                idx--;
                updateFlavor();
            }
        });
        nextBtn.addEventListener("click", function() {
            if (idx < flavorTexts.length - 1) {
                idx++;
                updateFlavor();
            }
        });
    }
});
