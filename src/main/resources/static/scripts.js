document.addEventListener("DOMContentLoaded", function() {
    const startButton = document.getElementById("startIndexing");
    const stopButton = document.getElementById("stopIndexing");

    startButton.addEventListener("click", function() {
        fetch("/api/startIndexing")
            .then(response => response.json())
            .then(data => {
                if (data.result) {
                    alert("Индексация запущена!");
                }
            })
            .catch(error => console.error("Ошибка:", error));
    });

    stopButton.addEventListener("click", function() {
        fetch("/api/stopIndexing")
            .then(response => response.json())
            .then(data => {
                if (data.result) {
                    alert("Индексация остановлена!");
                }
            })
            .catch(error => console.error("Ошибка:", error));
    });

    // Функция для обновления статистики
    function updateStatistics() {
        fetch("/api/statistics")
            .then(response => response.json())
            .then(data => {
                document.getElementById("sitesCount").textContent = data.statistics.sites;
                document.getElementById("pagesCount").textContent = data.statistics.pages;
                document.getElementById("lemmasCount").textContent = data.statistics.lemmas;
            })
            .catch(error => console.error("Ошибка:", error));
    }

    updateStatistics();
    setInterval(updateStatistics, 5000); // Обновляем статистику каждые 5 секунд
});
