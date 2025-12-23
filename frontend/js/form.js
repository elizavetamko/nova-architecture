/**
 * Nova Architecture - Form Module
 * Handles contact form validation and submission
 */

'use strict';

document.addEventListener('DOMContentLoaded', function() {
    initContactForm();
});

function initContactForm() {
    const form = document.getElementById('contact-form');

    if (!form) return;

    const fields = {
        name: form.querySelector('#name'),
        email: form.querySelector('#email'),
        phone: form.querySelector('#phone'),
        message: form.querySelector('#message')
    };

    const submitBtn = form.querySelector('button[type="submit"]');
    const formMessage = document.createElement('div');
    formMessage.className = 'form-message';
    formMessage.style.display = 'none';
    form.insertBefore(formMessage, form.firstChild);

    // Validation rules
    const validationRules = {
        name: {
            required: true,
            minLength: 2,
            maxLength: 100,
            message: {
                required: "Будь ласка, введіть ваше ім'я",
                minLength: "Ім'я має містити мінімум 2 символи",
                maxLength: "Ім'я має містити максимум 100 символів"
            }
        },
        email: {
            required: true,
            pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
            message: {
                required: 'Будь ласка, введіть email',
                pattern: 'Введіть коректну email адресу'
            }
        },
        phone: {
            required: false,
            pattern: /^(\+380)?[0-9]{9,10}$/,
            message: {
                pattern: 'Введіть коректний номер телефону (+380XXXXXXXXX)'
            }
        },
        message: {
            required: true,
            minLength: 10,
            maxLength: 1000,
            message: {
                required: 'Будь ласка, введіть повідомлення',
                minLength: 'Повідомлення має містити мінімум 10 символів',
                maxLength: 'Повідомлення має містити максимум 1000 символів'
            }
        }
    };

    /**
     * Validate a single field
     */
    function validateField(fieldName, value) {
        const rules = validationRules[fieldName];
        const errors = [];

        if (!rules) return errors;

        // Required check
        if (rules.required && (!value || value.trim() === '')) {
            errors.push(rules.message.required);
            return errors;
        }

        // Skip other validations if field is empty and not required
        if (!value || value.trim() === '') {
            return errors;
        }

        // Min length check
        if (rules.minLength && value.length < rules.minLength) {
            errors.push(rules.message.minLength);
        }

        // Max length check
        if (rules.maxLength && value.length > rules.maxLength) {
            errors.push(rules.message.maxLength);
        }

        // Pattern check
        if (rules.pattern && !rules.pattern.test(value)) {
            errors.push(rules.message.pattern);
        }

        return errors;
    }

    /**
     * Show field error
     */
    function showFieldError(field, errors) {
        const formGroup = field.closest('.form-group');

        if (!formGroup) return;

        // Remove existing error
        clearFieldError(field);

        if (errors.length > 0) {
            formGroup.classList.add('error');

            const errorElement = document.createElement('span');
            errorElement.className = 'error-message';
            errorElement.textContent = errors[0];
            formGroup.appendChild(errorElement);
        }
    }

    /**
     * Clear field error
     */
    function clearFieldError(field) {
        const formGroup = field.closest('.form-group');

        if (!formGroup) return;

        formGroup.classList.remove('error');
        const errorElement = formGroup.querySelector('.error-message');

        if (errorElement) {
            errorElement.remove();
        }
    }

    /**
     * Validate all fields
     */
    function validateForm() {
        let isValid = true;

        Object.keys(fields).forEach(fieldName => {
            const field = fields[fieldName];

            if (!field) return;

            const errors = validateField(fieldName, field.value);
            showFieldError(field, errors);

            if (errors.length > 0) {
                isValid = false;
            }
        });

        return isValid;
    }

    /**
     * Show form message
     */
    function showMessage(type, message) {
        formMessage.className = `form-message form-message--${type}`;
        formMessage.textContent = message;
        formMessage.style.display = 'block';

        // Scroll to message
        formMessage.scrollIntoView({ behavior: 'smooth', block: 'center' });

        // Hide message after delay (for success)
        if (type === 'success') {
            setTimeout(() => {
                formMessage.style.display = 'none';
            }, 5000);
        }
    }

    /**
     * Set loading state
     */
    function setLoading(loading) {
        if (loading) {
            submitBtn.disabled = true;
            submitBtn.innerHTML = '<span class="spinner spinner--small"></span> Надсилання...';
        } else {
            submitBtn.disabled = false;
            submitBtn.innerHTML = 'Надіслати';
        }
    }

    /**
     * Submit form
     */
    async function submitForm(e) {
        e.preventDefault();

        // Hide previous message
        formMessage.style.display = 'none';

        // Validate form
        if (!validateForm()) {
            return;
        }

        // Prepare data
        const formData = {
            name: fields.name.value.trim(),
            email: fields.email.value.trim(),
            phone: fields.phone ? fields.phone.value.trim() : '',
            message: fields.message.value.trim()
        };

        // Set loading state
        setLoading(true);

        try {
            const API_URL = 'http://localhost:8080';
            const response = await fetch(`${API_URL}/api/contact`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            const result = await response.json();

            if (response.ok && result.success) {
                showMessage('success', result.message || "Дякуємо! Ваше повідомлення надіслано. Ми зв'яжемося з вами найближчим часом.");
                form.reset();
            } else {
                // Handle validation errors from server
                if (result.errors) {
                    Object.keys(result.errors).forEach(fieldName => {
                        const field = fields[fieldName];
                        if (field) {
                            showFieldError(field, [result.errors[fieldName]]);
                        }
                    });
                }
                showMessage('error', result.message || 'Помилка відправки. Спробуйте пізніше.');
            }
        } catch (error) {
            console.error('Form submission error:', error);
            showMessage('error', 'Помилка з\'єднання. Перевірте підключення до інтернету та спробуйте ще раз.');
        } finally {
            setLoading(false);
        }
    }

    // Event listeners

    // Form submission
    form.addEventListener('submit', submitForm);

    // Real-time validation on blur
    Object.keys(fields).forEach(fieldName => {
        const field = fields[fieldName];

        if (!field) return;

        field.addEventListener('blur', function() {
            const errors = validateField(fieldName, field.value);
            showFieldError(field, errors);
        });

        // Clear error on focus
        field.addEventListener('focus', function() {
            clearFieldError(field);
        });

        // Real-time validation while typing (with debounce)
        let timeout;
        field.addEventListener('input', function() {
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                if (field.closest('.form-group').classList.contains('error')) {
                    const errors = validateField(fieldName, field.value);
                    showFieldError(field, errors);
                }
            }, 500);
        });
    });

    // Character counter for message
    if (fields.message) {
        const maxLength = validationRules.message.maxLength;
        const counterElement = document.createElement('span');
        counterElement.className = 'char-counter';
        counterElement.textContent = `0 / ${maxLength}`;

        const formGroup = fields.message.closest('.form-group');
        if (formGroup) {
            formGroup.appendChild(counterElement);
        }

        fields.message.addEventListener('input', function() {
            const length = this.value.length;
            counterElement.textContent = `${length} / ${maxLength}`;

            if (length > maxLength * 0.9) {
                counterElement.classList.add('char-counter--warning');
            } else {
                counterElement.classList.remove('char-counter--warning');
            }

            if (length > maxLength) {
                counterElement.classList.add('char-counter--error');
            } else {
                counterElement.classList.remove('char-counter--error');
            }
        });
    }
}

// Add styles for character counter
const style = document.createElement('style');
style.textContent = `
    .char-counter {
        display: block;
        text-align: right;
        font-size: 12px;
        color: var(--color-concrete, #8A8A7A);
        margin-top: 8px;
    }
    .char-counter--warning {
        color: var(--color-warning, #CC6600);
    }
    .char-counter--error {
        color: var(--color-error, #CC0000);
    }
`;
document.head.appendChild(style);

// Export for use in main.js
window.initContactForm = initContactForm;
