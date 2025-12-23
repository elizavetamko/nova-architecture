/**
 * Nova Architecture - Scroll Animations Module
 * Handles scroll-triggered animations using Intersection Observer
 */

'use strict';

function initScrollAnimations() {
    // Check for reduced motion preference
    const prefersReducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;

    if (prefersReducedMotion) {
        // Show all elements without animation
        document.querySelectorAll('.animate-on-scroll, .animate-fade-up, .animate-fade-down, .animate-fade-left, .animate-fade-right, .animate-scale-in, .animate-fade-in').forEach(el => {
            el.classList.add('is-visible');
        });
        return;
    }

    // Configuration
    const config = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    /**
     * Intersection Observer callback
     */
    function handleIntersection(entries, observer) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('is-visible');

                // Optionally unobserve after animation
                // observer.unobserve(entry.target);
            } else {
                // Optional: Remove class when out of view (for re-animation)
                // entry.target.classList.remove('is-visible');
            }
        });
    }

    // Create observer
    const observer = new IntersectionObserver(handleIntersection, config);

    // Observe elements with animation classes
    const animatedElements = document.querySelectorAll(
        '.animate-on-scroll, ' +
        '.animate-fade-up, ' +
        '.animate-fade-down, ' +
        '.animate-fade-left, ' +
        '.animate-fade-right, ' +
        '.animate-scale-in, ' +
        '.animate-fade-in, ' +
        '.line-draw, ' +
        '.split-text, ' +
        '.image-reveal'
    );

    animatedElements.forEach(el => observer.observe(el));

    // Counter animations
    initCounterAnimations();

    // Stagger children animations
    initStaggerAnimations();
}

/**
 * Initialize counter animations
 */
function initCounterAnimations() {
    const counters = document.querySelectorAll('[data-counter]');

    if (counters.length === 0) return;

    const counterObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const counter = entry.target;
                const target = parseInt(counter.dataset.counter, 10);
                const suffix = counter.dataset.suffix || '';
                const duration = parseInt(counter.dataset.duration, 10) || 2000;

                animateValue(counter, 0, target, duration, suffix);
                observer.unobserve(counter);
            }
        });
    }, {
        threshold: 0.5
    });

    counters.forEach(counter => counterObserver.observe(counter));
}

/**
 * Animate numeric value
 */
function animateValue(element, start, end, duration, suffix) {
    const startTime = performance.now();
    const range = end - start;

    function update(currentTime) {
        const elapsed = currentTime - startTime;
        const progress = Math.min(elapsed / duration, 1);

        // Easing function (ease-out-quart)
        const easeProgress = 1 - Math.pow(1 - progress, 4);

        const current = Math.round(start + range * easeProgress);
        element.textContent = current + suffix;

        if (progress < 1) {
            requestAnimationFrame(update);
        }
    }

    requestAnimationFrame(update);
}

/**
 * Initialize stagger animations for children elements
 */
function initStaggerAnimations() {
    const staggerContainers = document.querySelectorAll('[data-stagger]');

    staggerContainers.forEach(container => {
        const delay = parseFloat(container.dataset.stagger) || 0.1;
        const children = container.children;

        Array.from(children).forEach((child, index) => {
            child.style.transitionDelay = `${index * delay}s`;
        });
    });
}

/**
 * Text split animation - splits text into characters for animation
 */
function initTextSplit() {
    const splitTextElements = document.querySelectorAll('.split-text');

    splitTextElements.forEach(element => {
        const text = element.textContent;
        element.textContent = '';

        text.split('').forEach((char, index) => {
            const span = document.createElement('span');
            span.className = 'char';
            span.textContent = char === ' ' ? '\u00A0' : char;
            span.style.transitionDelay = `${index * 0.03}s`;
            element.appendChild(span);
        });
    });
}

/**
 * Scroll progress indicator
 */
function initScrollProgress() {
    const progressBar = document.querySelector('.scroll-progress');

    if (!progressBar) return;

    function updateProgress() {
        const scrollTop = window.pageYOffset;
        const docHeight = document.documentElement.scrollHeight - window.innerHeight;
        const progress = (scrollTop / docHeight) * 100;

        progressBar.style.width = `${progress}%`;
    }

    window.addEventListener('scroll', updateProgress, { passive: true });
    updateProgress();
}

/**
 * Reveal sections on scroll
 */
function initSectionReveal() {
    const sections = document.querySelectorAll('.section[data-reveal]');

    if (sections.length === 0) return;

    const sectionObserver = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('section--revealed');
            }
        });
    }, {
        threshold: 0.15,
        rootMargin: '0px 0px -100px 0px'
    });

    sections.forEach(section => sectionObserver.observe(section));
}

/**
 * Lazy load background images
 */
function initLazyBackgrounds() {
    const lazyBackgrounds = document.querySelectorAll('[data-bg]');

    if (lazyBackgrounds.length === 0) return;

    const bgObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const element = entry.target;
                const bgUrl = element.dataset.bg;

                element.style.backgroundImage = `url('${bgUrl}')`;
                element.classList.add('bg-loaded');
                observer.unobserve(element);
            }
        });
    }, {
        rootMargin: '200px 0px'
    });

    lazyBackgrounds.forEach(el => bgObserver.observe(el));
}

/**
 * Scroll-triggered class toggle
 */
function initScrollClass() {
    const scrollElements = document.querySelectorAll('[data-scroll-class]');

    if (scrollElements.length === 0) return;

    const classObserver = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            const className = entry.target.dataset.scrollClass;

            if (entry.isIntersecting) {
                entry.target.classList.add(className);
            } else {
                if (entry.target.dataset.scrollToggle === 'true') {
                    entry.target.classList.remove(className);
                }
            }
        });
    }, {
        threshold: 0.3
    });

    scrollElements.forEach(el => classObserver.observe(el));
}

/**
 * Initialize all scroll animations
 */
function initAllScrollAnimations() {
    initScrollAnimations();
    initTextSplit();
    initScrollProgress();
    initSectionReveal();
    initLazyBackgrounds();
    initScrollClass();
}

// Export for use in main.js
window.initScrollAnimations = initScrollAnimations;
window.initAllScrollAnimations = initAllScrollAnimations;
window.initTextSplit = initTextSplit;
window.initScrollProgress = initScrollProgress;
