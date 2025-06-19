// src/main/resources/static/js/detail.js

document.addEventListener("DOMContentLoaded", () => {
  const container = document.getElementById("detail-container");

  // Извлекаем закодированное название фильма из URL
  const parts = window.location.pathname.split("/");
  const encodedTitle = parts[parts.length - 1];
  const title = decodeURIComponent(encodedTitle);

  // Загрузка и парсинг CSV
  Papa.parse("/data/imdb_movies.csv", {
    download: true,
    header: true,
    skipEmptyLines: true,
    complete: results => {
      const all = results.data;
      // Ищем строку, где поле names совпадает с title
      const row = all.find(r => r.names === title);
      if (!row) {
        container.innerHTML = `
          <div class="alert alert-warning">
            Фильм «${title}» не найден в базе данных.
          </div>
        `;
        return;
      }

      // Если нашли, рендерим все данные
      container.innerHTML = `
        <h1 class="mb-4">${row.names}</h1>
        <div class="row mb-3">
          <div class="col-md-6">
            <p><strong>Оригинальное название:</strong> ${row.orig_title || "—"}</p>
            <p><strong>Жанр:</strong> ${row.genre || "—"}</p>
            <p><strong>Год выпуска:</strong> ${row.date_x || "—"}</p>
            <p><strong>IMDB-рейтинг:</strong> ${row.score || "—"}</p>
          </div>
          <div class="col-md-6">
            <p><strong>Статус:</strong> ${row.status || "—"}</p>
            <p><strong>Язык:</strong> ${row.orig_lang || "—"}</p>
            <p><strong>Бюджет:</strong> ${row.budget_x || "—"}</p>
            <p><strong>Сборы:</strong> ${row.revenue || "—"}</p>
            <p><strong>Страна:</strong> ${row.country || "—"}</p>
          </div>
        </div>
        <hr>
        <h4>Описание</h4>
        <p>${row.overview || "—"}</p>
        <hr>
        <h4>Режиссёр / Актёры / Сценаристы</h4>
        <p>${row.crew || "—"}</p>
        <a href="/movies" class="btn btn-secondary mt-4">← Назад к списку фильмов</a>
      `;
    },
    error: err => {
      console.error("Ошибка при загрузке CSV:", err);
      container.innerHTML = `
        <div class="alert alert-danger">
          Ошибка загрузки данных: ${err.message}
        </div>
      `;
    }
  });
});
