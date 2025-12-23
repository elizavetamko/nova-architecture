/**
 * Nova Architecture - Parallax Module
 * Handles parallax scrolling effects
 */

'use strict';

function initParallax() {
    // Check if reduced motion is preferred
    const prefersReducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;

    // Check if device is touch/mobile
    const isMobile = window.innerWidth < 768 || 'ontouchstart' in window;

    // Don't initialize parallax on mobile or if reduced motion is preferred
    if (prefersReducedMotion || isMobile) {
        return;
    }

    const parallaxElements = document.querySelectorAll('[data-parallax]');
    const heroBackground = document.querySelector('.hero__background');

    let ticking = false;

    /**
     * Update parallax positions
     */
    function updateParallax() {
        const scrolled = window.pageYOffset;
        const windowHeight = window.innerHeight;

        // Hero background parallax
        if (heroBackground) {
            const heroSection = heroBackground.closest('.hero');
            const heroRect = heroSection.getBoundingClientRect();

            // Only apply parallax when hero is visible
            if (heroRect.bottom > 0 && heroRect.top < windowHeight) {
                const parallaxValue = scrolled * 0.4;
                heroBackground.style.transform = `translate3d(0, ${parallaxValue}px, 0)`;
            }
        }

        // General parallax elements
        parallaxElements.forEach(element => {
            const rect = element.getBoundingClientRect();
            const speed = parseFloat(element.dataset.parallax) || 0.3;

            // Only apply parallax when element is visible
            if (rect.bottom > 0 && rect.top < windowHeight) {
                const yPos = (rect.top - windowHeight / 2) * speed;
                element.style.transform = `translate3d(0, ${yPos}px, 0)`;
            }
        });

        ticking = false;
    }

    /**
     * Request animation frame for smooth parallax
     */
    function onScroll() {
        if (!ticking) {
            requestAnimationFrame(updateParallax);
            ticking = true;
        }
    }

    // Initialize
    window.addEventListener('scroll', onScroll, { passive: true });

    // Initial call
    updateParallax();

    // Handle resize
    let resizeTimeout;
    window.addEventListener('resize', function() {
        clearTimeout(resizeTimeout);
        resizeTimeout = setTimeout(function() {
            // Disable parallax on mobile after resize
            if (window.innerWidth < 768) {
                window.removeEventListener('scroll', onScroll);

                // Reset transforms
                if (heroBackground) {
                    heroBackground.style.transform = '';
                }
                parallaxElements.forEach(element => {
                    element.style.transform = '';
                });
            }
        }, 250);
    });
}

/**
 * Advanced parallax for images with fade effect
 */
function initImageParallax() {
    const parallaxImages = document.querySelectorAll('.parallax-image');

    if (window.matchMedia('(prefers-reduced-motion: reduce)').matches || window.innerWidth < 768) {
        return;
    }

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('parallax-active');
            }
        });
    }, {
        threshold: 0.1,
        rootMargin: '0px'
    });

    parallaxImages.forEach(image => observer.observe(image));

    // Parallax on scroll
    window.addEventListener('scroll', function() {
        parallaxImages.forEach(container => {
            if (!container.classList.contains('parallax-active')) return;

            const rect = container.getBoundingClientRect();
            const windowHeight = window.innerHeight;

            if (rect.bottom > 0 && rect.top < windowHeight) {
                const scrollPercent = (windowHeight - rect.top) / (windowHeight + rect.height);
                const image = container.querySelector('img');

                if (image) {
                    const translateY = (scrollPercent - 0.5) * 50;
                    image.style.transform = `translate3d(0, ${translateY}px, 0) scale(1.1)`;
                }
            }
        });
    }, { passive: true });
}

/**
 * Mouse-based parallax for elements
 */
function initMouseParallax() {
    const mouseElements = document.querySelectorAll('[data-mouse-parallax]');

    if (mouseElements.length === 0 || window.innerWidth < 1024) {
        return;
    }

    document.addEventListener('mousemove', function(e) {
        const mouseX = e.clientX / window.innerWidth - 0.5;
        const mouseY = e.clientY / window.innerHeight - 0.5;

        mouseElements.forEach(element => {
            const speed = parseFloat(element.dataset.mouseParallax) || 20;
            const x = mouseX * speed;
            const y = mouseY * speed;

            element.style.transform = `translate3d(${x}px, ${y}px, 0)`;
        });
    });
}

// Export for use in main.js
window.initParallax = initParallax;
window.initImageParallax = initImageParallax;
window.initMouseParallax = initMouseParallax;
