document.addEventListener("DOMContentLoaded", function() {
    const backToTopButton = document.getElementById("back-to-top");
    const header = document.getElementById("header");
  
    if (!header || !backToTopButton) {
        console.error("Essential elements not found!");
        return;
    }
  
    function handleScroll() {
      if (window.pageYOffset > 50) {
        backToTopButton.classList.add("show");
        header.classList.add("fixed");
      } else {
        backToTopButton.classList.remove("show");
        header.classList.remove("fixed");
      }
    }
  
    backToTopButton.addEventListener("click", () => {
      window.scrollTo({
        top: 0,
        behavior: 'smooth'
      });
    });
  
    window.addEventListener("scroll", handleScroll);
  });
  