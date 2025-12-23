/**
 * Nova Architecture - Portfolio Filter Module
 * Handles portfolio filtering and load more functionality
 */

'use strict';

document.addEventListener('DOMContentLoaded', function() {
    initPortfolioFilter();
    initLoadMore();
});

function initPortfolioFilter() {
    const filterButtons = document.querySelectorAll('.filter-btn');
    const portfolioItems = document.querySelectorAll('.portfolio-grid .project-card');
    const portfolioGrid = document.querySelector('.portfolio-grid');

    if (filterButtons.length === 0 || portfolioItems.length === 0) return;

    /**
     * Filter portfolio items
     */
    function filterItems(category) {
        portfolioItems.forEach((item, index) => {
            const itemCategory = item.dataset.category;
            const shouldShow = category === 'all' || itemCategory === category;

            if (shouldShow) {
                item.style.display = '';
                item.style.opacity = '0';
                item.style.transform = 'translateY(20px)';

                // Staggered animation
                setTimeout(() => {
                    item.style.transition = 'opacity 0.4s ease, transform 0.4s ease';
                    item.style.opacity = '1';
                    item.style.transform = 'translateY(0)';
                }, index * 50);
            } else {
                item.style.opacity = '0';
                item.style.transform = 'translateY(20px)';

                setTimeout(() => {
                    item.style.display = 'none';
                }, 400);
            }
        });

        // Update grid layout after filtering
        if (portfolioGrid) {
            setTimeout(() => {
                // Trigger layout recalculation if using masonry
                if (window.Masonry && portfolioGrid.masonry) {
                    portfolioGrid.masonry.layout();
                }
            }, 500);
        }
    }

    /**
     * Set active filter button
     */
    function setActiveFilter(button) {
        filterButtons.forEach(btn => btn.classList.remove('filter-btn--active'));
        button.classList.add('filter-btn--active');
    }

    // Event listeners
    filterButtons.forEach(button => {
        button.addEventListener('click', function() {
            const category = this.dataset.filter;

            // Update URL hash for shareable filters
            if (history.pushState) {
                history.pushState(null, null, '#' + category);
            }

            setActiveFilter(this);
            filterItems(category);
        });
    });

    // Check for hash on page load
    function checkHashFilter() {
        const hash = window.location.hash.slice(1);

        if (hash) {
            const targetButton = document.querySelector(`.filter-btn[data-filter="${hash}"]`);

            if (targetButton) {
                setActiveFilter(targetButton);
                filterItems(hash);
            }
        }
    }

    // Handle browser back/forward
    window.addEventListener('popstate', checkHashFilter);

    // Initial check
    checkHashFilter();
}

/**
 * Load More functionality
 */
function initLoadMore() {
    const loadMoreBtn = document.querySelector('.load-more .btn');
    const portfolioGrid = document.querySelector('.portfolio-grid');

    if (!loadMoreBtn || !portfolioGrid) return;

    // Configuration
    const config = {
        itemsPerPage: 6,
        currentPage: 1,
        totalPages: 3 // Simulated total pages
    };

    // Sample project data (in real app, this would come from API)
    const additionalProjects = [
        {
            category: 'commercial',
            image: 'images/portfolio/project-7.jpg',
            title: 'Бізнес-парк "Інновація"',
            categoryLabel: 'Комерційна',
            year: '2023'
        },
        {
            category: 'residential',
            image: 'images/portfolio/project-8.jpg',
            title: 'Котеджне містечко "Затишок"',
            categoryLabel: 'Житлова',
            year: '2023'
        },
        {
            category: 'cultural',
            image: 'images/portfolio/project-9.jpg',
            title: 'Галерея сучасного мистецтва',
            categoryLabel: 'Культурна',
            year: '2022'
        },
        {
            category: 'commercial',
            image: 'images/portfolio/project-10.jpg',
            title: 'Готельний комплекс "Карпати"',
            categoryLabel: 'Комерційна',
            year: '2022'
        },
        {
            category: 'residential',
            image: 'images/portfolio/project-11.jpg',
            title: 'Приватний будинок "Модерн"',
            categoryLabel: 'Житлова',
            year: '2022'
        },
        {
            category: 'interior',
            image: 'images/portfolio/project-12.jpg',
            title: 'Інтер\'єр пентхаусу',
            categoryLabel: 'Інтер\'єр',
            year: '2021'
        }
    ];

    /**
     * Create project card HTML
     */
    function createProjectCard(project) {
        const card = document.createElement('article');
        card.className = 'project-card animate-fade-up';
        card.dataset.category = project.category;

        card.innerHTML = `
            <img src="${project.image}" alt="${project.title}" class="project-card__image"
                 onerror="this.src='images/placeholder.jpg'">
            <div class="project-card__overlay">
                <span class="project-card__category label">${project.categoryLabel}</span>
                <h3 class="project-card__title">${project.title}</h3>
                <span class="project-card__year">${project.year}</span>
            </div>
        `;

        return card;
    }

    /**
     * Load more items
     */
    function loadMore() {
        // Set loading state
        loadMoreBtn.disabled = true;
        loadMoreBtn.innerHTML = '<span class="spinner spinner--small"></span> Завантаження...';

        // Simulate API call delay
        setTimeout(() => {
            const startIndex = (config.currentPage - 1) * config.itemsPerPage;
            const endIndex = Math.min(startIndex + config.itemsPerPage, additionalProjects.length);
            const projectsToAdd = additionalProjects.slice(startIndex, endIndex);

            // Add new items
            projectsToAdd.forEach((project, index) => {
                const card = createProjectCard(project);
                card.style.opacity = '0';
                card.style.transform = 'translateY(20px)';
                portfolioGrid.appendChild(card);

                // Animate in
                setTimeout(() => {
                    card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
                    card.style.opacity = '1';
                    card.style.transform = 'translateY(0)';
                }, index * 100);
            });

            // Update page counter
            config.currentPage++;

            // Reset button state
            loadMoreBtn.disabled = false;
            loadMoreBtn.textContent = 'Показати ще';

            // Check if more items available
            if (config.currentPage > config.totalPages || startIndex + config.itemsPerPage >= additionalProjects.length) {
                loadMoreBtn.style.display = 'none';

                // Show "no more items" message
                const noMoreMessage = document.createElement('p');
                noMoreMessage.className = 'text-center text-muted';
                noMoreMessage.textContent = 'Ви переглянули всі проєкти';
                loadMoreBtn.parentElement.appendChild(noMoreMessage);
            }
        }, 800);
    }

    // Event listener
    loadMoreBtn.addEventListener('click', loadMore);
}

/**
 * Initialize Masonry layout (if library is loaded)
 */
function initMasonry() {
    const grid = document.querySelector('.portfolio-grid');

    if (!grid || typeof Masonry === 'undefined') return;

    // Wait for images to load
    imagesLoaded(grid, function() {
        grid.masonry = new Masonry(grid, {
            itemSelector: '.project-card',
            columnWidth: '.project-card',
            percentPosition: true,
            gutter: 30
        });
    });
}

// Export for use in main.js
window.initPortfolioFilter = initPortfolioFilter;
window.initLoadMore = initLoadMore;
window.initMasonry = initMasonry;
