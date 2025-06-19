// src/main/resources/static/js/profile.js

document.addEventListener("DOMContentLoaded", function() {
  const tbody = document.querySelector('#profileRatings tbody');
  const countEl = document.getElementById('countRated');
  const avgEl   = document.getElementById('avgScore');
  const lastEl  = document.getElementById('lastMovie');

  function getSavedRatings() {
    return JSON.parse(localStorage.getItem('ratings') || '{}');
  }
  function saveRatings(obj) {
    localStorage.setItem('ratings', JSON.stringify(obj));
  }

  function renderProfile() {
    tbody.innerHTML = '';
    const ratings = getSavedRatings();
    const entries = Object.entries(ratings); // [ [title, score], ... ]

    if (entries.length === 0) {
      countEl.textContent = 0;
      avgEl.textContent = '-';
      lastEl.textContent = '-';
      tbody.innerHTML = '<tr><td colspan="4">Нет оцененных фильмов.</td></tr>';
      return;
    }

    // Статистика
    const scores = entries.map(e => e[1]);
    const count = scores.length;
    const avg = (scores.reduce((a,b)=>a+b,0)/count).toFixed(1);
    const last = entries[entries.length - 1][0];

    countEl.textContent = count;
    avgEl.textContent = avg;
    lastEl.textContent = last;

    // Рендер строк
    entries.forEach(([title, score]) => {
      const tr = document.createElement('tr');

      // Название
      const tdTitle = document.createElement('td');
      const a = document.createElement('a');
      a.href = `/movies/${encodeURIComponent(title)}`;
      a.textContent = title;
      tdTitle.appendChild(a);
      tr.appendChild(tdTitle);

      // Оценка
      const tdScore = document.createElement('td');
      tdScore.textContent = score;
      tr.appendChild(tdScore);

      // Дата (время последней оценки пока текущее)
      const tdDate = document.createElement('td');
      tdDate.textContent = new Date().toLocaleString('ru-RU');
      tr.appendChild(tdDate);

      // Кнопка удаления
      const tdDel = document.createElement('td');
      const btn = document.createElement('button');
      btn.className = 'btn btn-sm btn-outline-danger';
      btn.textContent = '✕';
      btn.title = 'Удалить из списка';
      btn.addEventListener('click', () => {
        if (!confirm(`Удалить оценку для "${title}"?`)) return;
        delete ratings[title];
        saveRatings(ratings);
        renderProfile();  // перерисуем всё заново
      });
      tdDel.appendChild(btn);
      tr.appendChild(tdDel);

      tbody.appendChild(tr);
    });
  }

  renderProfile();
});
