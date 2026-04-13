const MAX_CARDS = 20;
const updatesList = document.getElementById("updates-list");
const emptyState = document.getElementById("empty-state");
const connectionStatus = document.getElementById("connection-status");
const connectButton = document.getElementById("connect-btn");
const disconnectButton = document.getElementById("disconnect-btn");

let stompClient;

function updateConnectionStatus(connected) {
    connectionStatus.textContent = connected ? "Conectado" : "Desconectado";
    connectionStatus.classList.toggle("status-pill--online", connected);
    connectionStatus.classList.toggle("status-pill--offline", !connected);
    connectButton.disabled = connected;
    disconnectButton.disabled = !connected;
}

function resolveCardVariant(temperature) {
    if (temperature <= 18) {
        return "update-card--cold";
    }

    if (temperature <= 27) {
        return "update-card--warm";
    }

    return "update-card--hot";
}

function renderUpdate(payload) {
    emptyState.classList.add("empty-state--hidden");

    const card = document.createElement("article");
    card.className = `update-card ${resolveCardVariant(payload.temperatura)}`;
    card.innerHTML = `
        <h3 class="update-card__city">${payload.cidade}</h3>
        <p class="update-card__temperature">${payload.temperatura.toFixed(1)}°C</p>
        <p class="update-card__description">${payload.descricao}</p>
        <p class="update-card__time">Recebido às ${payload.horario}</p>
    `;

    updatesList.prepend(card);

    while (updatesList.children.length > MAX_CARDS) {
        updatesList.removeChild(updatesList.lastElementChild);
    }
}

function connect() {
    if (stompClient?.active) {
        return;
    }

    stompClient = new StompJs.Client({
        webSocketFactory: () => new SockJS("/ws-clima"),
        reconnectDelay: 5000,
        debug: () => {}
    });

    stompClient.onConnect = () => {
        updateConnectionStatus(true);
        stompClient.subscribe("/topic/clima", (message) => {
            renderUpdate(JSON.parse(message.body));
        });
    };

    stompClient.onStompError = () => {
        updateConnectionStatus(false);
    };

    stompClient.onWebSocketClose = () => {
        updateConnectionStatus(false);
    };

    stompClient.activate();
}

function disconnect() {
    if (stompClient) {
        stompClient.deactivate();
    }

    updateConnectionStatus(false);
}

connectButton.addEventListener("click", connect);
disconnectButton.addEventListener("click", disconnect);

updateConnectionStatus(false);
connect();
