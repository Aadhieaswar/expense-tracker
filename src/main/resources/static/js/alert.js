const MAX_ALERTS = 5;

window.addEventListener('DOMContentLoaded', () => {
    const alertsContainer = document.getElementById('alerts-container');
    const alert_body = (content) =>
    (`<div class="d-flex">
        <div class="toast-body">
            ${content}
        </div>
    </div>
    `);

    window.addAlert = (alert_info, color) => {
        const alert_toast = document.createElement('div');
        alert_toast.classList.add("toast", "align-items-center", "text-white", `bg-${color ?? 'primary'}`, "border-0", "show", "ephemeral");

        alert_toast.innerHTML = alert_body(alert_info, color);
        
        alertsContainer.append(alert_toast);

        setTimeout(() => { 
            alert_toast.classList.add("fade-out");

            alert_toast.addEventListener('transitionend', () => {
                alert_toast.remove()
            });
        }, 3000);

        const alerts = alertsContainer.querySelectorAll(".toast");
        if (alerts.length > MAX_ALERTS)
            alerts[0].remove();
    }
});
