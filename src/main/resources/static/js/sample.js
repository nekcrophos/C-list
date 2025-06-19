

var nav = document.createElement('nav');
nav.className = 'navbar navbar-expand-lg border-bottom bg-body-tertiary';

var container = document.createElement('div');
container.className = 'container-fluid';

var brand = document.createElement('a');
brand.className = 'navbar-brand';
brand.href = '#';
brand.textContent = 'C-list';

var button = document.createElement('button');
button.className = 'navbar-toggler';
button.type = 'button';
button.setAttribute('data-bs-toggle', 'collapse');
button.setAttribute('data-bs-target', '#navbarNavAltMarkup');
button.setAttribute('aria-controls', 'navbarNavAltMarkup');
button.setAttribute('aria-expanded', 'false');
button.setAttribute('aria-label', 'Toggle navigation');

var span = document.createElement('span');
span.className = 'navbar-toggler-icon';

var collapse = document.createElement('div');
collapse.className = 'collapse navbar-collapse';
collapse.id = 'navbarNavAltMarkup';

var navNav = document.createElement('div');
navNav.className = 'navbar-nav';

var home = document.createElement('a');
home.className = 'nav-link';

home.href = '/';
home.textContent = 'Главная';

var films = document.createElement('a');
films.className = 'nav-link';
films.href = '/movies';
films.textContent = 'Фильмы';

var profile = document.createElement('a');
profile.className = 'nav-link';
profile.href = '/profile';
profile.textContent = 'Моя страница';

// Создаем элементы для поиска
// var searchNav = document.createElement('nav');
// searchNav.className = 'navbar bg-body-tertiary';

// var fluidContainer = document.createElement('div');
// fluidContainer.className = 'container-fluid';

var formFlex = document.createElement('form');
formFlex.className = 'd-flex';
formFlex.role = 'search';

var input = document.createElement('input');
input.className = 'form-control me-2';
input.type = 'search';
input.placeholder = 'Поиск';
input.ariaLabel = 'Search';

var buttonSearch = document.createElement('button');
buttonSearch.className = 'btn btn-outline-dark';
buttonSearch.type = 'submit';
buttonSearch.textContent = 'Поиск';

// Добавляем элементы в DOM - поиска
formFlex.appendChild(input);
formFlex.appendChild(buttonSearch);

formFlex.addEventListener("submit", function(e) {
  e.preventDefault();
  const q = input.value.trim();
  // если пусто — просто идём на /movies без параметра
  if (q) {
    window.location.href = `/movies?search=${encodeURIComponent(q)}`;
  } else {
    window.location.href = "/movies";
  }
});
// fluidContainer.appendChild(formFlex);
// searchNav.appendChild(fluidContainer);

// Добавляем элементы в DOM - navbar
button.appendChild(span);
navNav.appendChild(home);
navNav.appendChild(films);
navNav.appendChild(profile);

collapse.appendChild(navNav);
collapse.appendChild(formFlex );
container.appendChild(brand);
container.appendChild(button);
container.appendChild(collapse);

nav.appendChild(container);
document.body.appendChild(nav);

