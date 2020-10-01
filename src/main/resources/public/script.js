const main = () => {
  !!window.EventSource
    ? setupHandler()
    : console.warn("This client doesn't support SSE");
};

const setupHandler = () => {
  const evtSource = new EventSource("products/events");
  const eventList = document.querySelector("ul");

  const handler = ({ data }) => {
    const element = document.createElement("li");
    eventList.appendChild(setTextContent(element, data));
  };

  evtSource.onmessage = handler;
};

const setTextContent = (element, data) => {
  element.textContent = `Event: ${data}`;
  return element;
};

main();
