document.addEventListener("DOMContentLoaded", () => {
    const trailerModal = document.getElementById("trailerModal");
    if (trailerModal) {
        const iframe = trailerModal.querySelector("iframe");
        const originalSrc = iframe ? iframe.getAttribute("src") : "";

        trailerModal.addEventListener("hidden.bs.modal", () => {
            if (iframe) {
                iframe.setAttribute("src", "");
                iframe.setAttribute("src", originalSrc);
            }
        });
    }
});