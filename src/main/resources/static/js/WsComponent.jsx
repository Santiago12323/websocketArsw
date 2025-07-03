class WSClient extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            msg: ""
        };
    }

    componentDidMount() {
        this.wsocket = new WebSocket("ws://localhost:8080/timer");
        this.wsocket.onmessage = (evt) => this.onMessage(evt);
        this.wsocket.onerror = (evt) => this.onError(evt);
    }

    isSocketOpen() {
        return this.wsocket && this.wsocket.readyState === WebSocket.OPEN;
    }

    onMessage(evt) {
        console.log("In onMessage", evt);
        this.setState({ isLoaded: true, msg: evt.data });
    }

    onError(evt) {
        console.error("WebSocket Error", evt);
        this.setState({ error: evt });
    }

    render() {
        console.log("Rendering...");
        const { error, isLoaded, msg } = this.state;
        const socketStatus = this.isSocketOpen() ? "OPEN" : "NOT OPEN";

        if (error) {
            return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
            return <div>Loading...<p>WebSocket status: {socketStatus}</p></div>;
        } else {
            return (
                <div>
                    <h1>Server Time:</h1>
                    <p>{msg}</p>
                    <p>WebSocket status: {socketStatus}</p>
                </div>
            );
        }
    }
}

ReactDOM.render(<WSClient />, document.getElementById('timer'));
