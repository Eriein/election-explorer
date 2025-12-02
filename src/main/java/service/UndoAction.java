package service;

import entity.ElectionData;
import repository.DoublyLinkedList;

public class UndoAction {
    private Action action;
    private ElectionData data;
    private  DoublyLinkedList.Node<ElectionData> node;
    public UndoAction() {}

    public UndoAction(Action action, ElectionData data) {
        this.action = action;
        this.data = data;
    }
    public UndoAction(Action action, ElectionData data, DoublyLinkedList.Node<ElectionData> node) {
        this.action = action;
        this.data = data;
        this.node = node;
    }

    public DoublyLinkedList.Node<ElectionData> getNode() {
        return node;
    }

    public ElectionData getData() {
        return data;
    }
    public Action getAction() {
        return action;
    }
}
