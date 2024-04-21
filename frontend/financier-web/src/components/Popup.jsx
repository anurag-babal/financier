function Popup({title, onClose, component}) {
    return (
        <div className="modal" style={{ display: 'flex', backgroundColor: 'rgba(0, 0, 0, 0.6)' }}>
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h4 className="modal-title">{title}</h4>
                        <button type="button" className="btn btn-close" onClick={onClose}></button>
                    </div>
                    <div className="modal-body">
                        {component}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Popup;