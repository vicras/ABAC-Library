package com.vicras.abaclib.engine.model.attribute.impl.tree;

import com.vicras.abaclib.engine.model.attribute.TreeAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TreeAttributeNode<T> implements TreeAttribute<T> {
    private Class<T> attributeClass;
    private TreeAttribute<T> attribute;
    private List<Collection<TreeAttributeNode<T>>> child = new ArrayList<>();

    @Override
    public TreeAttribute<T> getParent() {
        return attribute.getParent();
    }

    @Override
    public String getName() {
        return attribute.getName();
    }

    @Override
    public String getFullName() {
        return attribute.getFullName();
    }
}
