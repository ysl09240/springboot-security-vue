package com.sykean.hmhc.util;


import com.sykean.hmhc.common.FrontTree;
import com.sykean.hmhc.common.MenuTree;
import com.sykean.hmhc.common.Tree;
import com.sykean.hmhc.common.TreeNode;
import com.sykean.hmhc.res.menu.MenuVO;

import java.util.*;

public class BuildTree {

    public static <T> Tree<T> build(List<Tree<T>> nodes) {

        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<Tree<T>>();

        for (Tree<T> children : nodes) {

            String pid = children.getParentId();
            if (pid == null || "-1".equals(pid)) {
                topNodes.add(children);

                continue;
            }

            for (Tree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setChildren(true);
                    continue;
                }
            }

        }

        Tree<T> root = new Tree<T>();
        if (topNodes.size() == 1) {
            root = topNodes.get(0);
        } else {
            root.setId("-1");
            root.setParentId("");
            root.setHasParent(false);
            root.setChildren(true);
            root.setChecked(true);
            root.setChildren(topNodes);
            root.setText("顶级节点");
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            root.setState(state);
        }

        return root;
    }

    public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes, String idParam) {
        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<>();

        for (Tree<T> children : nodes) {

            String pid = children.getParentId();
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);

                continue;
            }

            for (Tree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setChildren(true);

                    continue;
                }
            }

        }
        return topNodes;
    }


    public static <T> List<MenuTree<T>> buildMenuList(List<MenuTree<T>> nodes, String idParam) {
        if (nodes == null) {
            return null;
        }
        List<MenuTree<T>> topNodes = new ArrayList<>();

        for (MenuTree<T> children : nodes) {

            String pid = children.getParentId();
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);

                continue;
            }

            for (MenuTree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    parent.setHasChildren(true);

                    continue;
                }
            }

        }
        return topNodes;
    }
    public static <T> FrontTree<T> buildTree(List<FrontTree<T>> nodes,String idParam) {
        Map<String, Object> selected = new HashMap<>();
        selected.put("selected", true);
        selected.put("opened", true);
        Map<String, Object> unSelected = new HashMap<>();
        unSelected.put("selected", false);
        unSelected.put("opened", true);
        if (nodes == null) {
            return null;
        }
        if (nodes.size() == 1) {
            nodes.get(0).setState(selected);
            return nodes.get(0);
        }
        Set<FrontTree<T>> topNodes = new HashSet<>();
        //查询top节点
        for (FrontTree<T> children : nodes) {
            String pid = children.getParentId();
            String topid = children.getId();
            if (topid == null || topid.equals(idParam)) {
                topNodes.add(children);
                continue;
            }
            //添加子节点
            for (FrontTree<T> parent : nodes) {
                String id = parent.getValue();
                if (id != null && id.equals(pid)) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setChildren(true);
                    children.setState(unSelected);
                    break;
                }
            }
        }
        FrontTree<T> root = new FrontTree<T>();
        if (topNodes.size() == 1) {
            root = new ArrayList<>(topNodes).get(0);
            root.setState(selected);
        } else {
        	root.setId("-1");
            root.setValue("-1");
            root.setParentId("");
            root.setHasParent(false);
            root.setChildren(true);
            root.setChecked(true);
            root.setChildren(new ArrayList<>(topNodes));
            root.setLabel("顶级节点");
            root.setState(selected);
        }
        return root;
    }

    /**
     * children为空数据组的设为null
     *
     * @param menuTrees
     */
    public static void removeEmptyChildren(List<MenuTree<MenuVO>> menuTrees) {
        for (MenuTree menuTree : menuTrees) {
            if (menuTree.getChildren().isEmpty()) {
                menuTree.setChildren(null);
            } else {
                removeEmptyChildren(menuTree.getChildren());
            }
        }
    }

    public static <T> MenuTree<T> buildMenuTree(List<MenuTree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        if (nodes.size() == 1) {
            return nodes.get(0);
        }
        Set<MenuTree<T>> topNodes = new HashSet<>();
        //查询top节点
        for (MenuTree<T> children : nodes) {
            String pid = children.getParentId();
            for (MenuTree<T> top : nodes) {
                if (topNodes.contains(top)) {
                    continue;
                }
                if (top.getId().equals(pid)) {
                    topNodes.add(top);
                    continue;
                }
            }
            //添加子节点
            for (MenuTree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(children);
                    parent.setHasChildren(true);
                    break;
                }
            }
        }
        MenuTree<T> root = new MenuTree<T>();
        if (topNodes.size() == 1) {
            root = new ArrayList<>(topNodes).get(0);
        } else {
            root.setId("-1");
            root.setParentId("");
            root.setHasChildren(true);
            root.setChildren(new ArrayList<>(topNodes));
            root.setLabel("顶级节点");
        }
        return root;
    }

    public static List<MenuTree> bulidTree(List<MenuVO> menus) {
        List<MenuTree> trees = new ArrayList<>();
        for (MenuVO menu : menus) {
            MenuTree menuTree = BeanUtils.copyPropertiesByClass(menu, MenuTree.class);
            trees.add(menuTree);
        }
        return buildCustomTree(trees);
    }

    /**
     * 顶级节点未知，递归获取树
     *
     * @param treeNodes
     * @param <T>
     * @return
     */
    public static <T extends TreeNode> List<T> buildCustomTree(List<T> treeNodes) {
        if (treeNodes == null) {
            return null;
        }
        if (treeNodes.size() == 1) {
            return treeNodes;
        }
        List<T> topNodes = new ArrayList<>();

        //获取所有的topNodes
        for (T parent : treeNodes) {
            String pid = parent.getParentId();
            //查询top节点
            boolean isTop = true;
            for (T other : treeNodes) {
                if (other.getId().equals(pid)) {
                    isTop = false;
                }
            }
            if (isTop) {
                //再根据topNodes查询所有的子节点
                topNodes.add(parent);
                findChildren(parent, treeNodes);
            }
        }
        return topNodes;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private static <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<TreeNode>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}