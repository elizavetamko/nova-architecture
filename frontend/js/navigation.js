/**
 * Nova Architecture - Navigation Module
 * Handles header, mobile menu, and navigation functionality
 */

'use strict';

function initNavigation() {
    const header = document.querySelector('.header');
    const burger = document.querySelector('.burger');
    const mobileMenu = document.querySelector('.mobile-menu');
    const overlay = document.querySelector('.overlay');
    const navLinks = document.querySelectorAll('.nav__link, .mobile-menu__link');

    let lastScrollY = 0;
    let ticking = false;

    /**
     * Handle header scroll behavior
     */
    function handleHeaderScroll() {
        const currentScrollY = window.pageYOffset;

        if (currentScrollY > 100) {
            header.classList.add('header--scrolled');
        } else {
            header.classList.remove('header--scrolled');
        }

        // Optional: Hide header on scroll down, show on scroll up
        // if (currentScrollY > lastScrollY && currentScrollY > 200) {
        //     header.classList.add('header--hidden');
        // } else {
        //     header.classList.remove('header--hidden');
        // }

        lastScrollY = currentScrollY;
        ticking = false;
    }

    /**
     * Request animation frame for scroll
     */
    function onScroll() {
        if (!ticking) {
            requestAnimationFrame(handleHeaderScroll);
            ticking = true;
        }
    }

    /**
     * Toggle mobile menu
     */
    function toggleMobileMenu() {
        const isOpen = mobileMenu.classList.contains('mobile-menu--active');

        if (isOpen) {
            closeMobileMenu();
        } else {
            openMobileMenu();
        }
    }

    /**
     * Open mobile menu
     */
    function openMobileMenu() {
        burger.classList.add('burger--active');
        mobileMenu.classList.add('mobile-menu--active');

        if (overlay) {
            overlay.classList.add('overlay--active');
        }

        // Lock body scroll
        if (window.NovaUtils && window.NovaUtils.lockScroll) {
            window.NovaUtils.lockScroll();
        } else {
            document.body.style.overflow = 'hidden';
        }

        // Animate menu items
        const menuItems = mobileMenu.querySelectorAll('.mobile-menu__item');
        menuItems.forEach((item, index) => {
            item.style.transitionDelay = `${index * 0.1}s`;
            item.classList.add('is-visible');
        });
    }

    /**
     * Close mobile menu
     */
    function closeMobileMenu() {
        burger.classList.remove('burger--active');
        mobileMenu.classList.remove('mobile-menu--active');

        if (overlay) {
            overlay.classList.remove('overlay--active');
        }

        // Unlock body scroll
        if (window.NovaUtils && window.NovaUtils.unlockScroll) {
            window.NovaUtils.unlockScroll();
        } else {
            document.body.style.overflow = '';
        }

        // Reset menu items
        const menuItems = mobileMenu.querySelectorAll('.mobile-menu__item');
        menuItems.forEach(item => {
            item.style.transitionDelay = '';
            item.classList.remove('is-visible');
        });
    }

    /**
     * Set active navigation link based on current page
     */
    function setActiveNavLink() {
        const currentPath = window.location.pathname;
        const pageName = currentPath.split('/').pop() || 'index.html';

        navLinks.forEach(link => {
            const href = link.getAttribute('href');

            if (href === pageName ||
                (pageName === '' && href === 'index.html') ||
                (pageName === 'index.html' && href === 'index.html')) {
                link.classList.add('nav__link--active', 'mobile-menu__link--active');
            } else {
                link.classList.remove('nav__link--active', 'mobile-menu__link--active');
            }
        });
    }

    /**
     * Handle keyboard navigation
     */
    function handleKeydown(e) {
        // Close mobile menu on Escape
        if (e.key === 'Escape' && mobileMenu.classList.contains('mobile-menu--active')) {
            closeMobileMenu();
        }

        // Tab trapping in mobile menu
        if (e.key === 'Tab' && mobileMenu.classList.contains('mobile-menu--active')) {
            const focusableElements = mobileMenu.querySelectorAll(
                'a[href], button, textarea, input, select, [tabindex]:not([tabindex="-1"])'
            );
            const firstElement = focusableElements[0];
            const lastElement = focusableElements[focusableElements.length - 1];

            if (e.shiftKey) {
                if (document.activeElement === firstElement) {
                    e.preventDefault();
                    lastElement.focus();
                }
            } else {
                if (document.activeElement === lastElement) {
                    e.preventDefault();
                    firstElement.focus();
                }
            }
        }
    }

    // Event Listeners
    if (header) {
        window.addEventListener('scroll', onScroll, { passive: true });
    }

    if (burger && mobileMenu) {
        burger.addEventListener('click', toggleMobileMenu);
    }

    if (overlay) {
        overlay.addEventListener('click', closeMobileMenu);
    }

    // Close mobile menu on link click
    if (mobileMenu) {
        mobileMenu.querySelectorAll('a').forEach(link => {
            link.addEventListener('click', closeMobileMenu);
        });
    }

    // Keyboard navigation
    document.addEventListener('keydown', handleKeydown);

    // Set active nav link
    setActiveNavLink();

    // Close mobile menu on resize to desktop
    window.addEventListener('resize', function() {
        if (window.innerWidth > 1023 && mobileMenu && mobileMenu.classList.contains('mobile-menu--active')) {
            closeMobileMenu();
        }
    });

    // Initial check for header scroll state
    handleHeaderScroll();
}

// Export for use in main.js
window.initNavigation = initNavigation;
