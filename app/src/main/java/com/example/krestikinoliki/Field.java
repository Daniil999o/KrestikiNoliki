package com.example.krestikinoliki;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Objects;

public class Field {
    private ArrayList<FieldElement> _controlElements;

    private ArrayList<Vector2> _zeroPositions;
    private ArrayList<Vector2> _crossPositions;

    private FieldStyle _style;
    private Vector2 _size;

    public Field(Vector2 size, ArrayList<ImageView> controlImages) {
        _size = size;

        _controlElements = new ArrayList<>();

        int x = 0, y = 0;

        for (int i = 0; i < controlImages.size(); i++) {
            Vector2 currentPos = new Vector2(x, y);
            _controlElements.add(new FieldElement(currentPos, controlImages.get(i)));

            x++;

            if (x > 2) {
                x = 0;
                y++;
            }
        }

        _zeroPositions = new ArrayList<>();
        _crossPositions = new ArrayList<>();
    }

    public void setStyle(FieldStyle style) {
        _style = style;
    }

    public boolean placeObject(ImageView view, GameSettings.GameSide choice) {
        FieldElement element = getElement(view);

        return placeObject(element, choice);
    }

    public boolean placeOnPosition(Vector2 pos, GameSettings.GameSide choice) {
        FieldElement element = getElementByPosition(pos);

        if (element != null && isFree(pos))
            return placeObject(element, choice);

        return false;
    }

    public boolean placeObjectRandomly(GameSettings.GameSide choice) {
        return placeObject(getRandomFreeElement(), choice);
    }

    public boolean placeObject(FieldElement element, GameSettings.GameSide choice) {
        if (element == null || getSide(element) != GameSettings.GameSide.None)
            return false;

        (choice == GameSettings.GameSide.Zero ? _zeroPositions : _crossPositions).add(element.position);

        updateField();

        return true;
    }

    public void updateField() {
        for (int i = 0; i < _controlElements.size(); i++) {
            GameSettings.GameSide side = getSide(_controlElements.get(i));
            _controlElements.get(i).setImage(_style.sideDrawables.get(side));
        }
    }

    public boolean isFree(Vector2 pos) {
        if (_zeroPositions.contains(pos))
            return false;
        if (_crossPositions.contains(pos))
            return false;

        return true;
    }

    public FieldElement getRandomFreeElement() {
        ArrayList<FieldElement> elements = getFreeElements();

        if (elements.isEmpty()) return null;

        return elements.get(NormalRandom.range(0, elements.size()));
    }

    public boolean isEnd() {
        return !hasFreeElements() || getWinner() != GameSettings.GameSide.None;
    }

    public GameSettings.GameSide getWinner() {
        for (Combination combo : GameSettings.WIN_COMBINATIONS) {
            if (hasAllPositions(combo.positions, _zeroPositions))
                return GameSettings.GameSide.Zero;
            if (hasAllPositions(combo.positions, _crossPositions))
                return GameSettings.GameSide.Cross;
        }

        return GameSettings.GameSide.None;
    }

    public Vector2[] getSidePosition(GameSettings.GameSide side) {
        if (side == GameSettings.GameSide.Cross)
            return _crossPositions.toArray(new Vector2[0]);
        else if (side == GameSettings.GameSide.Zero)
            return _zeroPositions.toArray(new Vector2[0]);

        return new Vector2[0];
    }

    private boolean hasFreeElements() {
        return !getFreeElements().isEmpty();
    }

    private boolean hasAllPositions(Vector2[] positions, ArrayList<Vector2> currentPositions) {
        for (Vector2 pos1 : positions) {
            boolean found = false;

            for (Vector2 pos2 : currentPositions) {
                if (pos1.equals(pos2)) {
                    found = true;
                    break;
                }
            }

            if (!found)
                return false;
        }

        return true;
    }

    private FieldElement getElement(ImageView view) {

        for (int i = 0; i < _controlElements.size(); i++) {
            if (_controlElements.get(i).compareImage(view))
                return _controlElements.get(i);
        }

        return null;
    }

    private FieldElement getElementByPosition(Vector2 pos) {
        for (FieldElement element : _controlElements) {
            if (element.position.equals(pos))
                return element;
        }

        return null;
    }

    private GameSettings.GameSide getSide(FieldElement element) {
        if (_zeroPositions.contains(element.position))
            return GameSettings.GameSide.Zero;
        else if (_crossPositions.contains(element.position))
            return GameSettings.GameSide.Cross;

        return GameSettings.GameSide.None;
    }

    private ArrayList<FieldElement> getFreeElements() {
        ArrayList<FieldElement> elements = new ArrayList<>();

        for (FieldElement element : _controlElements) {
            if (getSide(element) == GameSettings.GameSide.None)
                elements.add(element);
        }

        return elements;
    }
}