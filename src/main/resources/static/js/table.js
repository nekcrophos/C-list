// src/main/resources/static/js/table.js

document.addEventListener("DOMContentLoaded", () => {
  const tbody       = document.querySelector("#moviesTable tbody");
  const loadMoreBtn = document.getElementById("loadMoreBtn");

  let all     = [];
  let filtered = [];
  let page    = 0;
  const pageSize = 10;

  // Читаем параметр search
  const params = new URLSearchParams(window.location.search);
  const search = params.get("search")?.toLowerCase() || "";

  // Считываем оценки из localStorage
  function getSavedRatings() {
    return JSON.parse(localStorage.getItem("ratings") || "{}");
  }
  function saveRating(title, score) {
    const ratings = getSavedRatings();
    ratings[title] = score;
    localStorage.setItem("ratings", JSON.stringify(ratings));
  }

  // Рендер порции
  function renderNextPage() {
    const start = page * pageSize;
    const slice = filtered.slice(start, start + pageSize);
    const ratings = getSavedRatings();

    slice.forEach(row => {
      const tr = document.createElement("tr");
      // ссылка на деталь
      const href = `/movies/${encodeURIComponent(row.names)}`;
      tr.innerHTML = `
        <td><a href="${href}" class="link-primary" style="text-decoration:none;">
             ${row.names}
           </a></td>
        <td>${row.score}</td>
        <td>${row.genre}</td>
        <td>${row.date_x}</td>
        <td>${ratings[row.names] || "-"}</td>
        <td><button class="btn btn-sm btn-outline-primary">Оценить</button></td>
      `;
      tr.querySelector("button").addEventListener("click", () => {
        const s = parseInt(prompt("Ваша оценка (1–10):"), 10);
        if (!s || s < 1 || s > 10) {
          alert("Неверная оценка");
          return;
        }
        saveRating(row.names, s);
        tr.children[4].textContent = s;
      });
      tbody.appendChild(tr);
    });

    page++;
    if (page * pageSize >= filtered.length) {
      loadMoreBtn.style.display = "none";
    }
  }

  // Загрузка CSV
  Papa.parse("/data/imdb_movies.csv", {
    download: true,
    header: true,
    skipEmptyLines: true,
    complete: results => {
      all = results.data;
      // если есть search, то фильтруем
      if (search) {
        filtered = all.filter(r =>
          r.names.toLowerCase().includes(search)
        );
      } else {
        filtered = all.slice(); // полная копия
      }
      // если ничего не найдено
      if (filtered.length === 0) {
        tbody.innerHTML = `<tr><td colspan="6">По запросу «${search}» ничего не найдено.</td></tr>`;
        loadMoreBtn.style.display = "none";
      } else {
        renderNextPage();
      }
    },
    error: err => {
      console.error("Ошибка загрузки CSV:", err);
      tbody.innerHTML = `<tr><td colspan="6">Ошибка загрузки данных.</td></tr>`;
      loadMoreBtn.style.display = "none";
    }
  });

  loadMoreBtn.addEventListener("click", renderNextPage);
});
