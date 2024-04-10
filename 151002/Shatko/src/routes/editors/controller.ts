import { Request, Response } from "express";
import { Editor } from "../../models";
import crudService from "../../services/crudService";
import { GetQueryParams, TABLES } from "../../types";

class EditorsController {
  getEditors = async (
    req: Request<unknown, unknown, unknown, GetQueryParams>,
    res: Response
  ) => {
    try {
      const result = await crudService.getAll<Editor>(
        TABLES.EDITORS,
        req.query
      );
      res.status(200).json(result);
    } catch (e) {
      res.status(500).json({ msg: "Error while getting editors" });
    }
  };

  getEditor = async (req: Request<{ id: string }>, res: Response) => {
    const { id } = req.params;
    try {
      if (!(await crudService.isEntityPresentByPk(id, TABLES.EDITORS))) {
        return res.status(404).json({ msg: "Editor not found" });
      }

      const result = await crudService.getByPk<Editor>(id, TABLES.EDITORS);
      res.status(200).json(result);
    } catch (e) {
      res.status(500).json({ msg: "Error while getting editor" });
    }
  };

  createEditor = async (
    req: Request<unknown, unknown, Omit<Editor, "id">>,
    res: Response
  ) => {
    try {
      if (
        await crudService.isEntityPresent(
          "login",
          req.body.login,
          TABLES.EDITORS
        )
      ) {
        return res
          .status(403)
          .json({ msg: "Editor with this login already exists" });
      }

      const result = await crudService.create<Editor>(req.body, TABLES.EDITORS);
      res.status(201).json(result);
    } catch (e) {
      res.status(500).json({ msg: "Error while creating editor" });
    }
  };

  deleteEditor = async (req: Request<{ id: string }>, res: Response) => {
    const { id } = req.params;
    try {
      if (!(await crudService.isEntityPresentByPk(id, TABLES.EDITORS))) {
        return res.status(404).json({ msg: "Editor not found" });
      }

      await crudService.delete(id, TABLES.EDITORS);
      res.status(204).json();
    } catch (e) {
      res.status(500).json({ msg: "Error while deleting editor" });
    }
  };

  updateEditor = async (
    req: Request<unknown, unknown, Editor>,
    res: Response
  ) => {
    const { id } = req.body;
    try {
      if (!(await crudService.isEntityPresentByPk(id, TABLES.EDITORS))) {
        return res.status(404).json({ msg: "Editor not found" });
      }

      if (
        await crudService.isEntityPresent(
          "login",
          req.body.login,
          TABLES.EDITORS
        )
      ) {
        return res
          .status(403)
          .json({ msg: "Editor with this login already exists" });
      }

      const result = await crudService.update<Editor>(req.body, TABLES.EDITORS);
      res.status(200).json(result);
    } catch (e) {
      res.status(500).json({ msg: "Error while updating editor" });
    }
  };
}

export default new EditorsController();
